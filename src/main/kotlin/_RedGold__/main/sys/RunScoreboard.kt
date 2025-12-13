package _RedGold__.main.sys

import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.getDataUuid
import _RedGold__.main.load.RequireJavaPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.ConcurrentHashMap

@RequireJavaPlugin
class RunScoreboard(private val plugin: JavaPlugin) {
    private var count = 0
    object PlayerDataCache {
        val gold: MutableMap<UUID, Long> = ConcurrentHashMap()
        val cash: MutableMap<UUID, Long> = ConcurrentHashMap()
        val kill: MutableMap<UUID, Long> = ConcurrentHashMap()
        val death: MutableMap<UUID, Long> = ConcurrentHashMap()

        val token: MutableMap<UUID, Long> = ConcurrentHashMap()
        val advancedToken: MutableMap<UUID, Long> = ConcurrentHashMap()
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

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
            for (uuid in Bukkit.getScheduler().callSyncMethod(plugin) { Bukkit.getOnlinePlayers().map { it.uniqueId }.toList() }.get()) {
                PlayerDataCache.gold[uuid] = getDataUuid(plugin, uuid, "gold").toLong()
                PlayerDataCache.cash[uuid] = getDataUuid(plugin, uuid, "cash").toLong()
                PlayerDataCache.kill[uuid] = getDataUuid(plugin, uuid, "kill").toLong()
                PlayerDataCache.death[uuid] = getDataUuid(plugin, uuid, "death").toLong()

                PlayerDataCache.token[uuid] = getDataUuid(plugin, uuid, "token/normal").toLong()
                PlayerDataCache.advancedToken[uuid] = getDataUuid(plugin, uuid, "token/advanced").toLong()
            }
        }, 0L, 20L)
    }
}