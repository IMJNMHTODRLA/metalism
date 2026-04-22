package _RedGold__.main.sys

import _RedGold__.main.function.Data.getDataUuid
import _RedGold__.main.functions.Scheduler.taskAsync
import _RedGold__.main.loads.RequireJavaPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@RequireJavaPlugin
class RunScoreboard(private val plugin: JavaPlugin) {
    private var count = 0
    object PlayerDataCache {
        val gold: MutableMap<UUID, Long> = ConcurrentHashMap()
        val cash: MutableMap<UUID, Long> = ConcurrentHashMap()
        val kill: MutableMap<UUID, Long> = ConcurrentHashMap()
        val death: MutableMap<UUID, Long> = ConcurrentHashMap()
    }

    init {
        Bukkit.getScheduler().runTaskTimer(plugin, Runnable{
            if (count == -1) count = 7
            for (player in Bukkit.getOnlinePlayers()) {
                Scoreboard().scoreboard(player, count)
                TabList().tabList(player, count)
            }
            count--
        }, 0L, 3L)

        plugin.taskAsync(0, 20) {
            for (uuid in Bukkit.getScheduler().callSyncMethod(plugin) { Bukkit.getOnlinePlayers().map { it.uniqueId }.toList() }.get()) {
                PlayerDataCache.gold[uuid] = getDataUuid(plugin, uuid, "gold").toLong()
            }
        }

        plugin.taskAsync(0, 100) {
            for (uuid in Bukkit.getScheduler().callSyncMethod(plugin) { Bukkit.getOnlinePlayers().map { it.uniqueId }.toList() }.get()) {
                PlayerDataCache.cash[uuid] = getDataUuid(plugin, uuid, "cash").toLong()
                Thread.sleep(500)
                PlayerDataCache.kill[uuid] = getDataUuid(plugin, uuid, "kill").toLong()
                Thread.sleep(500)
                PlayerDataCache.death[uuid] = getDataUuid(plugin, uuid, "death").toLong()
            }
        }
    }
}