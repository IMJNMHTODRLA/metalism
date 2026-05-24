package _RedGold__.main.commands.vip.upgradeRank

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

@RequireCommandExecutor("upgrade_rank", PermissionEnum.VIP, aliases = ["랭크업글"])
class UpgradeRank : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        sender.sendMsg("&a... 되겠어요? ( -_-)")
        return true
    }
}