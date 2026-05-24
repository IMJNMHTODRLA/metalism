package _RedGold__.main.commands.user.giveAdmin

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

@RequireCommandExecutor("give_admin", PermissionEnum.USER, aliases = ["어드민ON", "어드민모드"])
class GiveAdmin : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        sender.sendMsg("&a권한을 부여했습니다! ...는 꿈이었습니다.")
        return true
    }
}