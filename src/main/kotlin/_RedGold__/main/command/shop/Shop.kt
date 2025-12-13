package _RedGold__.main.command.shop

import _RedGold__.main.command.shop.sys.cashShop.cashGui.CashGui
import _RedGold__.main.command.shop.sys.dailyShop.dailyGui.DailyGui
import _RedGold__.main.command.shop.sys.goldShop.goldGui.GoldGui
import _RedGold__.main.command.shop.sys.monthlyShop.monthlyGui.MonthlyGui
import _RedGold__.main.command.shop.sys.selectGui.SelectGui
import _RedGold__.main.load.RequireCommandExecutor
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireTabExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

@RequireJavaPlugin
@RequireCommandExecutor("shop", "user")
@RequireTabExecutor
class Shop(private val plugin: JavaPlugin) : CommandExecutor, TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player ?: return false

        if (args.isEmpty()) SelectGui().openGui(player)
        else if (args[0] == "gold") GoldGui(plugin).openGui(player)
        else if (args[0] == "user") return true
        else if (args[0] == "daily") DailyGui(plugin).openGui(player)
        else if (args[0] == "monthly") MonthlyGui(plugin).openGui(player)
        else if (args[0] == "cash") CashGui(plugin).openGui(player)

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return listOf("gold", "user", "daily", "monthly", "cash")
        return emptyList()
    }
}