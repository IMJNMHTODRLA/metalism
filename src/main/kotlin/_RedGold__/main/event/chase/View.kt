package _RedGold__.main.event.chase

import _RedGold__.main.Main.Event.EVENT_CODE
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.allFileName
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.getDataUuid
import _RedGold__.main.function.Data.getDataUuidOrNull
import _RedGold__.main.function.Data.hasDataUuid
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import _RedGold__.main.sys.Scoreboard
import _RedGold__.main.sys.TabList
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

class View(private val plugin: JavaPlugin) {
    init {
        showActionBar()
        isJoin()
    }
    private val hasTarget: MutableMap<UUID, String?> = ConcurrentHashMap()
    private var allPlayer: Set<String> = setOf()

    private fun showActionBar() {
        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            for (player in Bukkit.getOnlinePlayers()) {
                if (hasTarget[player.uniqueId] == null) continue

                val targetPlayer = UUID.fromString(hasTarget[player.uniqueId]!!)

                val target = Bukkit.getPlayer(targetPlayer)
                if (target == null) {
                    if (allPlayer.isEmpty()) continue

                    val select = allPlayer
                        .shuffled(Random)
                        .take(1)

                    saveData(plugin, player, "chase/tracking", select[0])
                    continue
                }

                var distance = 0L
                val targetWorld = target.world

                if (player.world == target.world) distance = player.location.distance(target.location).toLong()

                player.sendActionBar(gc("&f&l${target.name}&8&l/&f&l월드: $targetWorld&8&l/&f&l$distance 블록"))
            }
        }, 0L, 20L)
    }

    private fun isJoin() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
            for (uuid in Bukkit.getScheduler().callSyncMethod(plugin) { Bukkit.getOnlinePlayers().map { it.uniqueId }.toList() }.get()) {
                hasTarget[uuid] = getDataUuidOrNull(plugin, uuid, "chase/tracking")
                allPlayer = allFileName(plugin, "chase/join")
            }
        }, 0L, 100L)
    }
}