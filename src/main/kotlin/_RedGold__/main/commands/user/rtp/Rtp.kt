package _RedGold__.main.commands.user.rtp

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.functions.launch
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.OVER_WORLD
import _RedGold__.main.managers.playerData.PermissionEnum
import com.github.shynixn.mccoroutine.bukkit.ticks
import kotlinx.coroutines.delay
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

@RequireTabExecutor
@RequireCommandExecutor("rtp", PermissionEnum.USER)
class Rtp : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false
        val uuid = player.uniqueId

        player.closeInventory()

        if (player.world.name != OVER_WORLD) {
            player.sendMsg("&crtp는 오버월드에서만 가능 합니다.")
            return true
        }

        val load = RtpValue.rtpCooldown[uuid]?: 0L
        val diff = now - load

        if (diff < RtpConst.COOLDOWN) {
            player.sendMsg("&c${RtpConst.COOLDOWN - diff}초 후에 rtp가 가능합니다.")
            return true
        }

        RtpValue.rtpCooldown[uuid] = now

        launch {
            (3 downTo 1).forEach {
                RtpConst.sendRtpMsg(player, "&e&l$it&f&l초 후에 순간이동 됩니다...")
                player.sendSound(Sound.BLOCK_NOTE_BLOCK_PLING)

                delay(20.ticks)
            }

            RtpConst.sendRtpMsg(player, "&a&l순간이동 완료!")
            player.sendSound(Sound.ENTITY_ENDERMAN_TELEPORT)

            val world = Bukkit.getWorld(OVER_WORLD)?: return@launch

            val x = RtpValue.threadLocalRandom.nextInt(-15001, 15001)
            val z = RtpValue.threadLocalRandom.nextInt(-15001, 15001)

            val highestY = world.getHighestBlockYAt(x, z) + 1.0

            val location = Location(world, x + 0.5, highestY, z + 0.5)
            player.teleportAsync(location)
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ) = emptyList<String>()
}
