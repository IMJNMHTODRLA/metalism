package _RedGold__.main.command.boost

import _RedGold__.main.command.boost.sys.admin.Admin
import _RedGold__.main.command.boost.sys.goDiscord.GoDiscord
import _RedGold__.main.command.boost.sys.infoGui.InfoGui
import _RedGold__.main.command.boost.sys.selectGui.SelectGui
import _RedGold__.main.load.RequireCommandExecutor
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireTabExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

@RequireCommandExecutor("boost", "user")
@RequireTabExecutor
@RequireJavaPlugin
class Boost(private val plugin: JavaPlugin) : CommandExecutor, TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player

        if (player == null) {
            if (args.size == 3) {
                if (args[1] == "add") Admin(plugin).addBoost(UUID.fromString(args[0]), args[2].toLong())
                if (args[1] == "remove") Admin(plugin).removeBoost(UUID.fromString(args[0]), args[2].toLong())
                if (args[1] == "set") Admin(plugin).setBoost(UUID.fromString(args[0]), args[2].toLong())
            }
            return true
        }

        if (args.isEmpty()) SelectGui().openGui(player)
        else if (args[0] == "info") InfoGui().openGui(player)
        else if (args[0] == "discord") GoDiscord().sendMsg(player)
        else if (args[0] == "plus") _RedGold__.main.command.boost.sys.plusTools.selectGui.SelectGui().openGui(player)
        else if (args.size == 3 && player.hasPermission("Main.owner")) {
            if (args[1] == "add") Admin(plugin).addBoost(UUID.fromString(args[0]), args[2].toLong())
            if (args[1] == "remove") Admin(plugin).removeBoost(UUID.fromString(args[0]), args[2].toLong())
            if (args[1] == "set") Admin(plugin).setBoost(UUID.fromString(args[0]), args[2].toLong())
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return listOf("info", "discord", "plus")
        return emptyList()
    }
}
