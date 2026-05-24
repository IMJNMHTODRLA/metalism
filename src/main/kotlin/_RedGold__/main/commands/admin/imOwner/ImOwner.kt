package _RedGold__.main.commands.admin.imOwner

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

@RequireCommandExecutor("im_owner", PermissionEnum.ADMIN, aliases = ["내가진정한운영자다"])
class ImOwner : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        sender.sendMsg("&a... 되겠어요? ( ㅡ_ㅡ)")
        return true
    }
}