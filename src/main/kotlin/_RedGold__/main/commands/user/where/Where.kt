package _RedGold__.main.commands.user.where

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

@RequireCommandExecutor("where", PermissionEnum.USER, aliases = ["어디임", "여기어디야", "여기가어디지"])
class Where : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        sender.sendMsg("&a컴퓨터 앞 의자에 앉아 계십니다. &a&l스트레칭이나 하세요.")
        return true
    }
}