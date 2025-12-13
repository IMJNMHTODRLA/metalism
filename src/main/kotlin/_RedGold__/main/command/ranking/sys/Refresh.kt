package _RedGold__.main.command.ranking.sys

import _RedGold__.main.command.ranking.sys.Refresh.RankValue.boostRank
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.deathRank
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.goldRank
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.isLoading
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.killRank
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.waitUpdate
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getDataUuid
import _RedGold__.main.load.RequireJavaPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.*

@RequireJavaPlugin
class Refresh(private val plugin: JavaPlugin) {
    object RankValue {
        var waitUpdate = 0L
        var isLoading = false
        var deathRank: List<Map.Entry<UUID, Long>> = emptyList()
        var goldRank: List<Map.Entry<UUID, Long>> = emptyList()
        var killRank: List<Map.Entry<UUID, Long>> = emptyList()
        var boostRank: List<Map.Entry<UUID, Long>> = emptyList()
    }

    private fun allLoad(rootName: String, noZero: Boolean = false): List<MutableMap. MutableEntry<UUID, Long>> {
        val deathDir = File(plugin.dataFolder, rootName)
        val deathCache: MutableMap<UUID, Long> = mutableMapOf()

        for (firstLevelDir in Objects.requireNonNull<Array<File>>(deathDir.listFiles { obj: File -> obj.isDirectory })) {
            for (uuidFile in Objects.requireNonNull<Array<File>>(firstLevelDir.listFiles { _: File?, name: String -> name.endsWith(".data")})) {
                val rawCowMeet = uuidFile.name.replace(".data", "")

                val formatUuid = StringBuilder(rawCowMeet)
                    .insert(8, '-')
                    .insert(13, '-')
                    .insert(18, '-')
                    .insert(23, '-')
                    .toString()

                val uuid = UUID.fromString(formatUuid)

                val value = getDataUuid(plugin, uuid, rootName).toLong()
                if (noZero && value == 0L) continue

                deathCache[uuid] = value
            }
        }

        Bukkit.getServer().logger.info("$rootName 반환 완료!")
        return deathCache.entries.sortedByDescending {it.value}
    }

    init {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
            Bukkit.getScheduler().runTask(plugin, Runnable {
                Bukkit.getServer().broadcastMessage(gc("&8순위 새로고침 중..."))
            })

            isLoading = true

            deathRank = allLoad("death")
            goldRank = allLoad("gold")
            killRank = allLoad("kill")
            boostRank = allLoad("boost", true)

            Bukkit.getScheduler().runTask(plugin, Runnable {
                waitUpdate = (System.currentTimeMillis() / 1000) + 60L * 10L
                isLoading = false

                Bukkit.getServer().broadcastMessage(gc("&8순위 새로고침 완료!"))
            })
        }, 0L, 20L * 60L * 10L)
    }
}