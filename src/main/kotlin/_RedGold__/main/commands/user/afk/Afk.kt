package _RedGold__.main.commands.user.afk

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

@RequireCommandExecutor("afk", PermissionEnum.USER, aliases = ["잠수"])
class Afk : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        sender.sendMsg("&a어차피 아무도 당신을 찾지 않으니 조용히 다녀오셔도 됩니다.")
        return true
    }
}