package _RedGold__.main.commands.user.ranking

import _RedGold__.main.commands.user.ranking.listeners.boostGui.BoostGui
import _RedGold__.main.commands.user.ranking.listeners.deathGui.DeathGui
import _RedGold__.main.commands.user.ranking.listeners.deathStreakGui.DeathStreakGui
import _RedGold__.main.commands.user.ranking.listeners.goldGui.GoldGui
import _RedGold__.main.commands.user.ranking.listeners.killGui.KillGui
import _RedGold__.main.commands.user.ranking.listeners.playTimeGui.PlayTimeGui
import _RedGold__.main.commands.user.ranking.listeners.selectGui.SelectGui
import _RedGold__.main.commands.user.ranking.listeners.killStreakGui.KillStreakGui
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

@RequireCommandExecutor("ranking", PermissionEnum.USER, aliases = ["랭킹", "순위"])
@RequireTabExecutor
class Ranking : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false

        when {
            args.isEmpty() -> SelectGui().openGui(player)

            args[0] == "gold" -> GoldGui().openGui(player, 0)
            args[0] == "boost" -> BoostGui().openGui(player, 0)
            args[0] == "playtime" -> PlayTimeGui().openGui(player, 0)

            args[0] == "kill" -> KillGui().openGui(player, 0)
            args[0] == "kill_streak" -> KillStreakGui().openGui(player, 0)

            args[0] == "death" -> DeathGui().openGui(player, 0)
            args[0] == "death_streak" -> DeathStreakGui().openGui(player, 0)
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return listOf("gold", "boost", "playtime", "kill", "kill_streak", "death", "death_streak")
        return emptyList()
    }
}
