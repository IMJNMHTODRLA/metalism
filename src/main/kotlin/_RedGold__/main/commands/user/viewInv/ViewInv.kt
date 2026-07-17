package _RedGold__.main.commands.user.viewInv

import _RedGold__.main.commands.user.viewInv.targetGui.TargetGui
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.NumberFormat.toUuidOrNull
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.viewInvManager.viewInvNoiseMap
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

@RequireCommandExecutor("viewinv", PermissionEnum.USER, aliases = ["vinv"])
@RequireTabExecutor
class ViewInv : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.size < 2) return false

        val player = sender as? Player?: return false
        val uuid = player.uniqueId

        val targetUUID = args[0].toUuidOrNull()?: return false
        val targetPlayer = Bukkit.getPlayer(targetUUID)?: return false
        val noise = args[1].toIntOrNull()?: return false

        val cooldown = ViewInvValue.cooldownMap[uuid]?: 0L
        if (cooldown > now) {
            player.fail("&c${cooldown - now}초 후에 다시 시도할 수 있습니다.")
            return true
        }

        ViewInvValue.cooldownMap[uuid] = now + 3

        if (viewInvNoiseMap[targetUUID] != noise) return false
        TargetGui().openGui(player, targetPlayer)
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ) = emptyList<String>()
}