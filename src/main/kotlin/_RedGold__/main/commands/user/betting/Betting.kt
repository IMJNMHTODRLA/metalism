package _RedGold__.main.commands.user.betting

import _RedGold__.main.commands.user.betting.listeners.coinGui.CoinGui
import _RedGold__.main.commands.user.betting.listeners.diceGui.DiceGui
import _RedGold__.main.commands.user.betting.listeners.highLow.HighLowGui
import _RedGold__.main.commands.user.betting.listeners.lottoGui.LottoGui
import _RedGold__.main.commands.user.betting.listeners.selectGui.SelectGui
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

@RequireCommandExecutor("betting", PermissionEnum.USER)
@RequireTabExecutor
class Betting : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false

        when {
            args.isEmpty() -> SelectGui().openGui(player)
            args[0] == "coin" -> CoinGui().openGui(player)
            args[0] == "dice" -> DiceGui().openGui(player)
            args[0] == "highlow" -> HighLowGui().openGui(player)
            args[0] == "lotto" -> LottoGui().openGui(player)
        }

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