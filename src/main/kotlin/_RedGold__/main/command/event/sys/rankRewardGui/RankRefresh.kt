package _RedGold__.main.command.event.sys.rankRewardGui

import _RedGold__.main.command.event.sys.rankRewardGui.RankRefresh.EventRank.eventTier
import _RedGold__.main.command.event.sys.rankRewardGui.RankRefresh.EventRank.isLoading
import _RedGold__.main.command.event.sys.rankRewardGui.RankRefresh.EventRank.waitUpdate
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.point
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.allFileName
import _RedGold__.main.function.Data.getDataUuid
import _RedGold__.main.function.Scheduler.task
import _RedGold__.main.function.Scheduler.taskAsync
import _RedGold__.main.function.api.toUuid
import _RedGold__.main.load.RequireJavaPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*

@RequireJavaPlugin
class RankRefresh(private val plugin: JavaPlugin) {
    object EventRank {
        var waitUpdate = 0L
        var isLoading = true
        var eventTier: List<Map.Entry<UUID, Long>> = emptyList()
    }
    
    private fun allLoad() {
        val cache: MutableMap<UUID, Long> = mutableMapOf()
        val allFileName = allFileName(plugin, "randomEffect/best_point")

        for (fileName in allFileName) {
            val uuid = fileName.toUuid()
            val value = getDataUuid(plugin, uuid, "randomEffect/best_point").toLong()

            if (value == 0L) continue
            cache[uuid] = value
        }

        Bukkit.getServer().logger.info("best_point 반환 완료!")
        eventTier = cache.entries.sortedByDescending {it.value}
    }

    init {
        plugin.taskAsync(20 * 30, 20 * 60 * 30) {
            plugin.task {
                Bukkit.getServer().broadcastMessage(gc("&8이벤트 순위 새로고침 중..."))
            }

            isLoading = true
            allLoad()

            plugin.task {
                waitUpdate = (System.currentTimeMillis() / 1000) + 60L * 30L
                isLoading = false

                Bukkit.getServer().broadcastMessage(gc("&8이벤트 순위 새로고침 완료!"))
            }
        }
    }
}