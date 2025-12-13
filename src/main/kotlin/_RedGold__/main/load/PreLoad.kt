package _RedGold__.main.load

import _RedGold__.main.function.Color.gc
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

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
annotation class RequireCommandExecutor(val commandName: String, val permission: String, val usage: String = "", val aliases: Array<String> = [])

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequireTabExecutor

object PreLoad {
    private fun JavaPlugin.registerCommand(name: String, executor: CommandExecutor, tabExecutor: TabExecutor? = null): Command {
        val constructor = Class.forName("org.bukkit.command.PluginCommand")
            .getDeclaredConstructor(String::class.java, org.bukkit.plugin.Plugin::class.java)
        constructor.isAccessible = true
        val cmd = constructor.newInstance(name, this) as org.bukkit.command.PluginCommand

        cmd.setExecutor(executor)
        if (tabExecutor != null) cmd.tabCompleter = tabExecutor

        val commandMapField = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
        commandMapField.isAccessible = true
        val commandMap = commandMapField.get(Bukkit.getServer()) as org.bukkit.command.CommandMap

        commandMap.register(this.name.lowercase(), cmd)
        return cmd
    }

    fun loadClass(plugin: JavaPlugin) {
        val scan = io.github.classgraph.ClassGraph()
            .enableClassInfo()
            .acceptPackages("_RedGold__.main")
            .scan()

        for (info in scan.allClasses) {
            val clazz = Class.forName(info.name).kotlin

            val hasOnInstance = clazz.hasAnnotation<OnInstance>()
            val hasPlugin = clazz.hasAnnotation<RequireJavaPlugin>()
            val hasListener = clazz.hasAnnotation<RequireListener>()
            val hasCommandExecutor = clazz.hasAnnotation<RequireCommandExecutor>()
            val hasTabExecutor = clazz.hasAnnotation<RequireTabExecutor>()

            if (!(hasOnInstance || hasPlugin || hasListener || hasCommandExecutor || hasTabExecutor)) continue

            val checkPlugin = clazz.constructors.firstOrNull {
                i -> i.parameters.any {it.type.classifier == JavaPlugin::class}
            }

            if (hasOnInstance) {
                clazz.createInstance()
                continue
            }

            val instance = if (checkPlugin != null) checkPlugin.call(plugin) else clazz.createInstance()

            if (hasListener && instance is org.bukkit.event.Listener) {
                plugin.server.pluginManager.registerEvents(instance, plugin)
            }

            if (hasCommandExecutor && instance is CommandExecutor) {
                val annotation = clazz.findAnnotation<RequireCommandExecutor>()!!

                val cmd = if (hasTabExecutor) plugin.registerCommand(annotation.commandName, instance, instance as TabExecutor)
                else plugin.registerCommand(annotation.commandName, instance)

                cmd.permission = "Main.${annotation.permission.lowercase()}"

                if (annotation.aliases.isNotEmpty()) {
                    cmd.aliases = annotation.aliases.toMutableList()

                    val commandMapField = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
                    commandMapField.isAccessible = true
                    val commandMap = commandMapField.get(Bukkit.getServer()) as org.bukkit.command.CommandMap

                    val prefix = plugin.name.lowercase()

                    for (alias in annotation.aliases) {
                        commandMap.register(alias.lowercase(), prefix, cmd)
                    }
                }
                if (annotation.usage != "") cmd.usage = gc(annotation.usage)
            }
        }

        scan.close()
    }
}