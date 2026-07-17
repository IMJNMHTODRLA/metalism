package _RedGold__.main.commands.user.ranking.listeners.refreshManager

import _RedGold__.main.commands.user.ranking.listeners.GlobalConst
import _RedGold__.main.commands.user.ranking.listeners.GlobalValue
import _RedGold__.main.functions.ExceptionSeverity
import _RedGold__.main.functions.PluginException
import _RedGold__.main.managers.rankingManager.getRanking
import _RedGold__.main.managers.playerData.PlayerManager
import org.bukkit.Bukkit
import org.bukkit.Statistic
import org.bukkit.entity.Player
import java.util.*

object RefreshConst {
    lateinit var onlinePlayers: Collection<Player>

    private fun <P, T : Comparable<T>, R> updateRank(
        currentKeys: Array<UUID>,
        currentValues: Any,
        playerAllData: Collection<P>,
        uuidSelector: (P) -> UUID,
        valueSelector: (P) -> T,
        resultFactory: (Array<UUID>, List<T>) -> R
    ): R {
        val currentMap = mutableMapOf<UUID, T>()

        currentKeys.forEachIndexed { i, uuid ->
            @Suppress("UNCHECKED_CAST")
            currentMap[uuid] = when (currentValues) {
                is LongArray -> currentValues[i]
                is IntArray -> currentValues[i]
                is FloatArray -> currentValues[i]

                else -> throw PluginException("Unsupported array type", ExceptionSeverity.HIGH)
            } as T
        }

        playerAllData.forEach { data ->
            currentMap[uuidSelector(data)] = valueSelector(data)
        }

        val sorted = currentMap.toList().sortedByDescending { it.second }

        return resultFactory(
            Array(sorted.size) { i -> sorted[i].first },
            sorted.map { it.second }
        )
    }

    fun rankingUpdate() {
        val allData = PlayerManager.getAllData()

        GlobalValue.goldRank = updateRank(
            GlobalValue.goldRank.keys, GlobalValue.goldRank.values,
            allData, { it.uuid }, { it.gold },
            { keys, values -> GlobalConst.LongRankingData(keys, values.toLongArray()) }
        ) //골드 랭킹

        GlobalValue.boostRank = updateRank(
            GlobalValue.boostRank.keys, GlobalValue.boostRank.values,
            allData, { it.uuid }, { it.boost },
            { keys, values -> GlobalConst.IntRankingData(keys, values.toIntArray()) }
        ) //후원 랭킹

        GlobalValue.playTimeRank = updateRank(
            GlobalValue.playTimeRank.keys, GlobalValue.playTimeRank.values,
            onlinePlayers, {it.uniqueId}, { it.getStatistic(Statistic.PLAY_ONE_MINUTE) / 1728000.0f },
            { keys, values -> GlobalConst.FloatRankingData(keys, values.toFloatArray()) }
        ) //플레이 타임 랭킹

        GlobalValue.killRank = updateRank(
            GlobalValue.killRank.keys, GlobalValue.killRank.values,
            allData, { it.uuid }, { it.combatData.kill },
            { keys, values -> GlobalConst.IntRankingData(keys, values.toIntArray()) }
        ) //킬 랭킹

        GlobalValue.killStreakRank = updateRank(
            GlobalValue.killStreakRank.keys, GlobalValue.killStreakRank.values,
            allData, { it.uuid }, { it.combatData.killStreak },
            { keys, values -> GlobalConst.IntRankingData(keys, values.toIntArray()) }
        ) //연킬 랭킹

        GlobalValue.deathRank = updateRank(
            GlobalValue.deathRank.keys, GlobalValue.deathRank.values,
            allData, { it.uuid }, { it.combatData.death },
            { keys, values -> GlobalConst.IntRankingData(keys, values.toIntArray()) }
        ) //데스 랭킹

        GlobalValue.deathStreakRank = updateRank(
            GlobalValue.deathStreakRank.keys, GlobalValue.deathStreakRank.values,
            allData, { it.uuid }, { it.combatData.deathStreak },
            { keys, values -> GlobalConst.IntRankingData(keys, values.toIntArray()) }
        ) //연데스 랭킹
    }

    private fun <P, T : Comparable<T>, R> initRank(
        allData: Collection<P>,
        uuidSelector: (P) -> UUID,
        valueSelector: (P) -> T,
        resultFactory: (Array<UUID>, List<T>) -> R
    ): R {
        val currentMap = mutableMapOf<UUID, T>()

        allData.forEach { data ->
            currentMap[uuidSelector(data)] = valueSelector(data)
        }

        val sorted = currentMap.toList().sortedByDescending { it.second }

        return resultFactory(
            Array(sorted.size) { i -> sorted[i].first },
            sorted.map { it.second }
        )
    }

    fun init() {
        val allData = getRanking()

        GlobalValue.goldRank = initRank(
            allData, {it.uuid}, {it.gold},
            { keys, values -> GlobalConst.LongRankingData(keys, values.toLongArray()) }
        )

        GlobalValue.boostRank = initRank(
            allData, {it.uuid}, {it.boost},
            { keys, values -> GlobalConst.IntRankingData(keys, values.toIntArray()) }
        )

        GlobalValue.playTimeRank = initRank(
            Bukkit.getOfflinePlayers().toList(), {it.uniqueId}, { it.getStatistic(Statistic.PLAY_ONE_MINUTE) / 1728000.0f },
            { keys, values -> GlobalConst.FloatRankingData(keys, values.toFloatArray()) }
        )

        GlobalValue.killRank = initRank(
            allData, {it.uuid}, { it.kill },
            { keys, values -> GlobalConst.IntRankingData(keys, values.toIntArray()) }
        )

        GlobalValue.killStreakRank = initRank(
            allData, {it.uuid}, { it.killStreak },
            { keys, values -> GlobalConst.IntRankingData(keys, values.toIntArray()) }
        )

        GlobalValue.deathRank = initRank(
            allData, {it.uuid}, { it.death },
            { keys, values -> GlobalConst.IntRankingData(keys, values.toIntArray()) }
        )

        GlobalValue.deathStreakRank = initRank(
            allData, {it.uuid}, { it.deathStreak },
            { keys, values -> GlobalConst.IntRankingData(keys, values.toIntArray()) }
        )
    }
}