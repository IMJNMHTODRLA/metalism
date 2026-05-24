package _RedGold__.main.commands.user.shop

import _RedGold__.main.commands.user.shop.listeners.dailyShop.dailyGui.DailyGui
import _RedGold__.main.commands.user.shop.listeners.goldShop.goldGui.GoldGui
import _RedGold__.main.commands.user.shop.listeners.monthlyShop.MonthlyGui
import _RedGold__.main.commands.user.shop.listeners.selectGui.SelectGui
import _RedGold__.main.commands.user.shop.listeners.userShop.userItemListGui.UserItemListGui
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

@RequireCommandExecutor("shop", PermissionEnum.USER, aliases = ["상점"])
@RequireTabExecutor
class Shop : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false

        when {
            args.isEmpty() -> SelectGui().openGui(player)
            args[0] == "gold" -> GoldGui().openGui(player)
            //args[0] == "crystal" -> CrystalGui(plugin).openGui(player)
            args[0] == "user" -> UserItemListGui().openGui(player, 0)
            args[0] == "daily" -> DailyGui().openGui(player)
            args[0] == "monthly" -> MonthlyGui().openGui(player)
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return listOf("gold", "crystal", "user", "daily", "monthly")
        return emptyList()
    }
}