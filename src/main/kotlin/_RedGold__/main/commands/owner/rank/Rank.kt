package _RedGold__.main.commands.owner.rank

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.NumberFormat.toUUIDOrNull
import _RedGold__.main.functions.getTabPlayers
import _RedGold__.main.functions.task
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor

@RequireCommandExecutor("rank", PermissionEnum.OWNER, "&c/<command> [name] [permission]", ["permission", "펄미션", "랭크"])
@RequireTabExecutor
class Rank : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.size < 2) return false
        if (args[1] !in PermissionEnum.entries.map { it.name }) return false

        val targetUUID = args[0].toUUIDOrNull()?: return false
        val targetRank = PermissionEnum[args[1]]?: return false

        taskAsync {
            val result = PermissionEnum.modify(targetUUID, targetRank)
            task {
                if (result) sender.sendMsg("&a펄미션 적용에 성공했습니다!(${targetRank.node})")
                else sender.sendMsg("&a펄미션 적용에 실패했습니다.(${targetRank.node})")
            }
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return getTabPlayers(args[0]) { it.name }
        else if (args.size == 2) return PermissionEnum.entries.map { it.name }

        return emptyList()
    }
}