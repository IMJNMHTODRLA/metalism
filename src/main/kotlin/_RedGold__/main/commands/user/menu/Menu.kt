package _RedGold__.main.commands.user.menu

import _RedGold__.main.commands.user.menu.menuGui.MenuGui
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

@RequireCommandExecutor("menu", PermissionEnum.USER)
@RequireTabExecutor
class Menu : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        MenuGui().openGui(sender as? Player?: return false)
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ) = emptyList<String>()
}