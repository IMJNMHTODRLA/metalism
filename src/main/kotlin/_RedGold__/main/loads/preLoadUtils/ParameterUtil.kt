package _RedGold__.main.loads.preLoadUtils

import _RedGold__.main.loads.RequireLinkPacketListener
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.findAnnotation

object ParameterUtil {
    fun resolveParameter(
        owner: KClass<*>,
        param: KParameter,
        plugin: JavaPlugin
    ): Any {
        val type = param.type.classifier as? KClass<*>
            ?: throw IllegalStateException("Unknown parameter type in ${owner.simpleName}")

        if (type == JavaPlugin::class) return plugin

        val linkAnn = owner.findAnnotation<RequireLinkPacketListener>()
        val linked = linkAnn?.listenerClass

        if (linked != null && type == linked) return false
        if (HelperClassUtil.isManagedClass(type)) return false

        throw IllegalStateException(
            "Cannot resolve dependency ${type.simpleName} in ${owner.simpleName}"
        )
    }
}