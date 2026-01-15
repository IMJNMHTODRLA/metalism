package _RedGold__.main.command.ranking.sys

import _RedGold__.main.command.ranking.sys.Refresh.RankValue.KILL_STREAK_MIN_SUBS
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.KILL_STREAK_MIN_GOLD
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.KILL_STREAK_MIN_TIMES
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.boostRank
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.cashRank
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.deathRank
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.goldRank
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.isLoading
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.killRank
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.killStreakRank
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.killStreakReward
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.playTimeRank
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.waitUpdate
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.allFileName
import _RedGold__.main.function.Data.getDataUuid
import _RedGold__.main.function.Scheduler.task
import _RedGold__.main.function.Scheduler.taskAsync
import _RedGold__.main.function.api.toUuid
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.sys.KillRespawn.KillStreakObject.killStreak
import org.bukkit.Bukkit
import org.bukkit.Statistic
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

@RequireJavaPlugin
class Refresh(private val plugin: JavaPlugin) {
    object RankValue {
        var waitUpdate = 0L
        var isLoading = false

        var goldRank: List<Map.Entry<UUID, Long>> = listOf()
        var cashRank: List<Map.Entry<UUID, Long>> = listOf()
        var playTimeRank: List<Map.Entry<UUID, Float>> = listOf()

        var boostRank: List<Map.Entry<UUID, Long>> = listOf()

        var killRank: List<Map.Entry<UUID, Long>> = listOf()
        var deathRank: List<Map.Entry<UUID, Long>> = listOf()
        var killStreakRank: List<Map.Entry<UUID, Int>> = listOf()
        const val KILL_STREAK_MIN_TIMES = 50f
        const val KILL_STREAK_MIN_SUBS = 4f
        const val KILL_STREAK_MIN_GOLD = 85.3f


        var killStreakReward: MutableMap<UUID, KillStreakReward> = mutableMapOf()
    }

    data class KillStreakReward(
        var gold: Float,
        var exp: Float,
    )

    private fun allLoad(rootName: String, noZero: Boolean = false): List<MutableMap. MutableEntry<UUID, Long>> {
        val cache: MutableMap<UUID, Long> = mutableMapOf()
        val allFileName = allFileName(plugin, rootName)

        for (fileName in allFileName) {
            val uuid = fileName.toUuid()
            val value = getDataUuid(plugin, uuid, rootName).toLong()

            if (noZero && value == 0L) continue
            cache[uuid] = value
        }

        Bukkit.getServer().logger.info("$rootName 반환 완료!")
        return cache.entries.sortedByDescending {it.value}
    }

    private fun totalGiveReward(totalPlayers: Int, rank: Int, percentage: Float): Pair<Float, Float> {
        val totalPrizePool = totalPlayers * (totalPlayers / KILL_STREAK_MIN_SUBS).coerceAtLeast(KILL_STREAK_MIN_TIMES)
        //0.31
        //0.23
        //0.19
        //0.14
        //0.09
        //0.04
        val halving = if (totalPlayers < 5) 2f
            else if (totalPlayers < 10) 1.5f
            else 1f

        val pizza = when {
            rank == 1 -> totalPrizePool * 0.31f
            rank in 2..5 -> totalPrizePool * 0.23f
            percentage <= 10.0f -> totalPrizePool * 0.19f
            percentage <= 30.0f -> totalPrizePool * 0.14f
            percentage <= 50.0f -> totalPrizePool * 0.09f
            else -> totalPrizePool * 0.04f
        }.coerceAtLeast(KILL_STREAK_MIN_GOLD) / halving

        val giveExp = when {
            rank == 1 -> 3.12f
            rank in 2..5 -> 2.62f
            percentage <= 10.0f -> 2.21f
            percentage <= 30.0f -> 1.63f
            percentage <= 50.0f -> 1.21f
            else -> 0.85f
        } / halving

        return pizza to giveExp
    }

    init {
        plugin.task(0, 20 * 60 * 1) {
            Bukkit.getServer().broadcastMessage(gc("&8순위 새로고침 중...(TYPE 0)"))

            killStreakRank = killStreak.entries
                .filter {it.value > 0}
                .sortedByDescending {it.value}

            val totalCount = killStreakRank.size
            if (totalCount == 0) return@task

            killStreakRank.forEachIndexed {index, entry ->
                val rank = index + 1
                val percentage = (rank.toFloat() / totalCount.toFloat()) * 100.0f
                val uuid = entry.key

                Bukkit.getPlayer(uuid)?: return@forEachIndexed
                val (gold, exp) = totalGiveReward(totalCount, rank, percentage)

                val reward = killStreakReward.getOrPut(uuid) { KillStreakReward(0f, 0f) }
                reward.gold += gold
                reward.exp += exp
            }

            Bukkit.getServer().broadcastMessage(gc("&8순위 새로고침 완료!(TYPE 0)"))
        }

        plugin.task(0, 20 * 60 * 10) {
            val offlinePlayer = Bukkit.getOfflinePlayers()
            Bukkit.getServer().broadcastMessage(gc("&8순위 새로고침 중...(TYPE 1)"))

            isLoading = true
            plugin.taskAsync {
                playTimeRank = offlinePlayer.filter { it.name != null }
                    .filter {!it.isBanned}
                    .filter {(System.currentTimeMillis() - it.lastLogin) < (1000L * 60 * 60 * 24 * 30 * 6)}
                    .associate {
                        val ticks = it.getStatistic(Statistic.PLAY_ONE_MINUTE).toFloat()
                        it.uniqueId to ticks / 1728000.0f
                    }
                    .entries
                    .sortedByDescending {it.value}

                deathRank = allLoad("death")
                goldRank = allLoad("gold")
                cashRank = allLoad("cash")
                killRank = allLoad("kill")
                boostRank = allLoad("boost", true)

                plugin.task {
                    isLoading = false
                    waitUpdate = (System.currentTimeMillis() / 1000) + 60L * 10L
                    Bukkit.getServer().broadcastMessage(gc("&8순위 새로고침 완료!(TYPE 1)"))
                }
            }
        }
    }
}