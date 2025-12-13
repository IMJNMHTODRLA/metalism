package _RedGold__.main.command.betting

import _RedGold__.main.command.betting.sys.coinGui.CoinGui
import _RedGold__.main.command.betting.sys.diceGui.DiceGui
import _RedGold__.main.command.betting.sys.highLow.HighLowGui
import _RedGold__.main.command.betting.sys.lottoGui.LottoGui
import _RedGold__.main.command.betting.sys.selectGui.SelectGui
import _RedGold__.main.load.RequireCommandExecutor
import _RedGold__.main.load.RequireTabExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

@RequireCommandExecutor("betting", "user")
@RequireTabExecutor
class Betting : CommandExecutor, TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player ?: return false

        if (args.isEmpty()) SelectGui().openGui(player)
        else if (args[0] == "coin") CoinGui().openGui(player)
        else if (args[0] == "dice") DiceGui().openGui(player)
        else if (args[0] == "highlow") HighLowGui().openGui(player)
        else if (args[0] == "lotto") LottoGui().openGui(player)

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