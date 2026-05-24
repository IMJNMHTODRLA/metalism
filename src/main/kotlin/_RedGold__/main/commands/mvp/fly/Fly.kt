package _RedGold__.main.commands.mvp.fly

import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.playerData.SPAWN_WORLD
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

@RequireCommandExecutor("fly", PermissionEnum.MVP)
@RequireTabExecutor
@RequireJavaPlugin
class Fly : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false
        if (player.world.name != SPAWN_WORLD) player.fail("&c오직 스폰에서만 사용 가능합니다.")

        player.isFlying = !player.isFlying

        if (player.isFlying) player.sendMsg("&f&lFlight &a&lEnable!")
        else player.sendMsg("&f&lFlight &c&lDisable!")

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ) = emptyList<String>()
}