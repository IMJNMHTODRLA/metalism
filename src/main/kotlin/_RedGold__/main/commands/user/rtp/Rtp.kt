package _RedGold__.main.commands.user.rtp

import _RedGold__.main.functions.Color.sendAction
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.Color.sendTitleMsg
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.OVER_WORLD
import _RedGold__.main.managers.playerData.PermissionEnum
import com.github.shynixn.mccoroutine.bukkit.launch
import com.github.shynixn.mccoroutine.bukkit.ticks
import kotlinx.coroutines.delay
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

@RequireJavaPlugin
@RequireTabExecutor
@RequireCommandExecutor("rtp", PermissionEnum.USER)
class Rtp(private val plugin: JavaPlugin) : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false
        val uuid = player.uniqueId
        val secureRandom = RtpValue.secureRandom

        if (player.world.name != OVER_WORLD) {
            player.sendMsg("&crtp는 오버월드에서만 가능 합니다.")
            return true
        }

        val now = now
        val load = RtpValue.rtpCooldown[uuid]?: 0L

        if (now - load < RtpConst.COOLDOWN) {
            player.sendMsg("&c${RtpConst.COOLDOWN - (now - load)}초 후에 rtp가 가능합니다.")
            return true
        }

        plugin.launch {
            (3 downTo 1).forEach { i ->
                player.sendMsg("$i&f&l초 후에 순간이동 됩니다...")
                player.sendAction("$i&f&l초 후에 순간이동 됩니다...")
                player.sendTitleMsg("", "$i&f&l초 후에 순간이동 됩니다...", 0, 20, 0)
                player.sendSound(Sound.BLOCK_NOTE_BLOCK_PLING)

                delay(20.ticks)
            }

            player.sendMsg("&a&l순간이동 완료!")
            player.sendAction("&a&l순간이동 완료!")
            player.sendTitleMsg("", "&a&l순간이동 완료!", 0, 20, 10)
            player.sendSound(Sound.ENTITY_ENDERMAN_TELEPORT)

            val world = Bukkit.getWorld(OVER_WORLD)?: return@launch

            val x = secureRandom.nextInt(-15001, 15001)
            val z = secureRandom.nextInt(-15001, 15001)

            val highestY = world.getHighestBlockYAt(x, z) + 1.0

            player.teleportAsync(Location(
                world, x + 0.5, highestY, z + 0.5
            ))

            RtpValue.rtpCooldown[uuid] = now
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
