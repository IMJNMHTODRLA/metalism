package _RedGold__.main.command.rtp

import _RedGold__.main.command.menu.sys.menuGui.MenuGui
import _RedGold__.main.function.Color.gc
import _RedGold__.main.load.RequireCommandExecutor
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireTabExecutor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.security.SecureRandom
import java.util.*
import java.util.concurrent.ThreadLocalRandom

@RequireJavaPlugin
@RequireTabExecutor
@RequireCommandExecutor("rtp", "user")
class Rtp(private val plugin: JavaPlugin) : CommandExecutor, TabExecutor {
    private val rtpLog: MutableMap<UUID, Long> = mutableMapOf()

    private fun motherFucker(player: Player, time: Int, prefix: String) {
        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            player.sendMessage(gc("$prefix&f&l초 후에 순간이동 됩니다..."))
            player.sendActionBar(gc("$prefix&f&l초 후에 순간이동 됩니다..."))
            player.sendTitle("", gc("$prefix&f&l초 후에 순간이동 됩니다..."), 0, 20, 0)
            player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
        }, 20L * time)
    }

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false
        val uuid = player.uniqueId

        if (player.world.name != "world") {
            player.sendMessage(gc("&crtp는 오직 오버월드에서만 가능 합니다."))
            return true
        }

        val now = System.currentTimeMillis() / 1000
        val load = rtpLog.getOrDefault(uuid, 1L)

        if (now - load < 600) {
            player.sendMessage(gc("&c${(600 - (now - load))}초 후에 rtp가 가능합니다."))
            return true
        }

        val x = SecureRandom().nextInt(-10000, 10000 + 1)
        val z = SecureRandom().nextInt(-10000, 10000 + 1)

        motherFucker(player, 0, "&e&l3")
        motherFucker(player, 1, "&e&l2")
        motherFucker(player, 2, "&e&l1")

        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            rtpLog[uuid] = now

            player.sendMessage(gc("&a&l순간이동 완료!"))
            player.sendActionBar(gc("&a&l순간이동 완료!"))
            player.sendTitle("", gc("&a&l순간이동 완료!"), 0, 20, 10)

            val world = Bukkit.getWorld("world")!!
            val highestY = world.getHighestBlockYAt(x, z) + 1.0

            player.teleport(Location(world, x + 0.5, highestY, z + 0.5))
            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f)
        }, 20L * 3)

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
