package _RedGold__.main.commands.user.cosmetic

import _RedGold__.main.commands.user.cosmetic.listeners.selectGui.SelectGui
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

@RequireCommandExecutor("cosmetic", PermissionEnum.USER)
@RequireTabExecutor
class Cosmetic : CommandExecutor, TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false

        SelectGui().openGui(player)
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ) = emptyList<String>()
}