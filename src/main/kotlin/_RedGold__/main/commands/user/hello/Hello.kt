package _RedGold__.main.commands.user.hello

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

@RequireCommandExecutor("hello", PermissionEnum.USER, aliases = ["안녕", "안녕하세요"])
class Hello : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        sender.sendMsg("&a왜 거기다 인사하세요.")
        return true
    }
}