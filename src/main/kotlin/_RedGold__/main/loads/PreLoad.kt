package _RedGold__.main.loads

import _RedGold__.main.loads.preLoadUtils.*
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class OnInstance

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequireJavaPlugin

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequireListener

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequireCommandExecutor(
    val commandName: String,
    val permission: PermissionEnum,
    val usage: String = "",
    val aliases: Array<String> = []
)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequireTabExecutor

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequirePacketListener

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequireLinkPacketListener(val listenerClass: KClass<*>)

class PreLoad(private val plugin: JavaPlugin) {
    fun load() {
        PreLoadValue.scannedClasses = ScanClassUtil.scanClasses()

        for (clazz in PreLoadValue.scannedClasses) {
            if (PreLoadValue.instanceMap.containsKey(clazz)) continue
            if (!HelperClassUtil.isLoadTarget(clazz)) continue

            try {
                init(clazz)
            } catch (e: Exception) {
                plugin.logger.severe("${clazz.simpleName} load failed: ${e.message}")
            }
        }
    }
    
    private fun init(clazz: KClass<*>): Any {
        PreLoadValue.instanceMap[clazz]?.let { return it }

        if (PreLoadValue.creating.contains(clazz)) {
            throw IllegalStateException("Circular dependency detected: ${clazz.simpleName}")
        }

        PreLoadValue.creating.add(clazz)
        val constructor = PreLoadValue.constructorCache.getOrPut(clazz) {
            clazz.primaryConstructor ?: clazz.constructors.firstOrNull()
            ?: throw IllegalStateException("${clazz.simpleName} has no constructor")
        }

        val args = constructor.parameters.map { param ->
            val resolved = ParameterUtil.resolveParameter(clazz, param, plugin)

            if (resolved is KClass<*>) init(resolved)
            else resolved
        }

        val instance = constructor.call(*args.toTypedArray())
            ?: error("${clazz.simpleName} instance creation failed")
        PreLoadValue.instanceMap[clazz] = instance

        PreLoadValue.creating.remove(clazz)
        RegisterUtil.registerFeatures(clazz, instance, plugin)

        return instance
    }
}