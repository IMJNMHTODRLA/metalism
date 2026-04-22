package _RedGold__.main.commands.user.mailbox

import _RedGold__.main.commands.user.mailbox.listeners.mailboxGui.MailboxGui
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

@RequireCommandExecutor("mailbox", PermissionEnum.USER, aliases = ["우편함"])
@RequireTabExecutor
@RequireJavaPlugin
class Mailbox(private val plugin: JavaPlugin) : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false

        MailboxGui(plugin).openGui(player, 0)
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> = emptyList()
}