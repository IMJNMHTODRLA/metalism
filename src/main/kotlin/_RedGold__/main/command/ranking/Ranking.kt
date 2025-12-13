package _RedGold__.main.command.ranking

import _RedGold__.main.command.ranking.sys.boostGui.BoostGui
import _RedGold__.main.command.ranking.sys.deathGui.DeathGui
import _RedGold__.main.command.ranking.sys.goldGui.GoldGui
import _RedGold__.main.command.ranking.sys.killGui.KillGui
import _RedGold__.main.command.ranking.sys.selectGui.SelectGui
import _RedGold__.main.load.RequireCommandExecutor
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireTabExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

@RequireCommandExecutor("ranking", "user")
@RequireTabExecutor
class Ranking : CommandExecutor, TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player ?: return false

        if (args.isEmpty()) SelectGui().openGui(player)
        else if (args[0] == "death") DeathGui().openGui(player, 0)
        else if (args[0] == "gold") GoldGui().openGui(player, 0)
        else if (args[0] == "kill") KillGui().openGui(player, 0)
        else if (args[0] == "boost") BoostGui().openGui(player, 0)

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return listOf("death", "gold", "kill", "boost")
        return emptyList()
    }
}
