package _RedGold__.main.command.style

import _RedGold__.main.command.style.sys.StyleGui
import _RedGold__.main.load.RequireCommandExecutor
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireTabExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

@RequireCommandExecutor("style", "user")
@RequireTabExecutor
@RequireJavaPlugin
class Style(private val plugin: JavaPlugin) : CommandExecutor, TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player ?: return false

        StyleGui(plugin).openGui(player)
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return listOf("coin", "dice", "highlow", "lotto")
        return emptyList()
    }
}