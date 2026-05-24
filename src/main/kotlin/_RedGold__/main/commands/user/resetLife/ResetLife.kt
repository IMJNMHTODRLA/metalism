package _RedGold__.main.commands.user.resetLife

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

@RequireCommandExecutor("reset_life", PermissionEnum.USER, aliases = ["인생초기화", "리셋라이프"])
class ResetLife : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        sender.sendMsg("&c자해는 나쁜 것입니다. &e대신 크리퍼를 만나러 가세요. ( •_•)")
        return true
    }
}