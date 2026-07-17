package _RedGold__.main.commands.user.jump

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

@RequireCommandExecutor("jump", PermissionEnum.USER, aliases = ["점프", "스페이스바"])
class Jump : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        sender.sendMsg("&a굳이 명령어로 점프를 해야 할 만큼 스페이스바가 무거우신가요?")
        return true
    }
}