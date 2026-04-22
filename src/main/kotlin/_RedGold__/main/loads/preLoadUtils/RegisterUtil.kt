package _RedGold__.main.loads.preLoadUtils

import _RedGold__.main.functions.Color.gc
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.loads.RequirePacketListener
import _RedGold__.main.loads.RequireTabExecutor
import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.event.PacketListenerCommon
import org.bukkit.Bukkit
import org.bukkit.command.*
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

object RegisterUtil {
    private fun JavaPlugin.registerCommand(
        name: String,
        executor: CommandExecutor,
        tabExecutor: TabExecutor? = null
    ): Command {
        val constructor = Class
            .forName("org.bukkit.command.PluginCommand")
            .getDeclaredConstructor(String::class.java, org.bukkit.plugin.Plugin::class.java)

        constructor.isAccessible = true

        val cmd = constructor.newInstance(name, this) as PluginCommand

        cmd.setExecutor(executor)
        if (tabExecutor != null) cmd.tabCompleter = tabExecutor

        val commandMapField = Bukkit.getServer().javaClass.getDeclaredField("commandMap")
        commandMapField.isAccessible = true

        val commandMap = commandMapField.get(Bukkit.getServer()) as CommandMap
        commandMap.register(this.name.lowercase(), cmd)

        return cmd
    }

    private fun registerCommand(
        clazz: KClass<*>,
        instance: CommandExecutor,
        plugin: JavaPlugin
    ) {
        val annotation = clazz.findAnnotation<RequireCommandExecutor>()!!
        val hasTab = clazz.hasAnnotation<RequireTabExecutor>()

        val cmd = if (hasTab && instance is TabExecutor) {
            plugin.registerCommand(annotation.commandName, instance, instance)
        }
        else {
            plugin.registerCommand(annotation.commandName, instance)
        }

        cmd.permission = annotation.permission.node.lowercase()

        if (annotation.aliases.isNotEmpty()) {
            cmd.aliases = annotation.aliases.toMutableList()
        }

        if (annotation.usage.isNotEmpty()) {
            cmd.usage = annotation.usage.gc()
        }
    }

    fun registerFeatures(
        clazz: KClass<*>,
        instance: Any,
        plugin: JavaPlugin
    ) {
        if (clazz.hasAnnotation<RequirePacketListener>() && instance is PacketListenerCommon) {
            PacketEvents.getAPI().eventManager.registerListener(instance)
        }

        if (clazz.hasAnnotation<RequireListener>() && instance is Listener) {
            plugin.server.pluginManager.registerEvents(instance, plugin)
        }

        if (clazz.hasAnnotation<RequireCommandExecutor>() && instance is CommandExecutor) {
            registerCommand(clazz, instance, plugin)
        }
    }
}