package _RedGold__.main.commands.user.chest

import _RedGold__.main.commands.user.chest.listeners.chestGui.ChestGui
import _RedGold__.main.commands.user.chest.listeners.selectGui.SelectGui
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.EasyPermission.permission
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

@RequireCommandExecutor("chest", PermissionEnum.USER, aliases = ["창고"])
@RequireTabExecutor
class Chest : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false

        when {
            args.isEmpty() -> SelectGui().openGui(player)
            else -> {
                val page = args[0].toIntOrNull()?: return true
                val requireRank = PermissionEnum.entries[page.coerceAtMost(3)]

                if (player.permission(requireRank)) {
                    ChestGui().openGui(player, page)
                } else {
                    player.sendMsg("${requireRank.prefix} &c랭크가 필요합니다!")
                }
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
        return emptyList()
    }
}