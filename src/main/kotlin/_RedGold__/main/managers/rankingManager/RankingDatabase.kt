package _RedGold__.main.managers.rankingManager

import _RedGold__.main.functions.NumberFormat.toUuid
import _RedGold__.main.managers.database.tableManager.playersDB.CombatStats
import _RedGold__.main.managers.database.tableManager.playersDB.DefaultStats
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun getRanking() =
    transaction {
        val defaultList = DefaultStats.selectAll().map { row ->
            Pair(row[DefaultStats.uuid], row[DefaultStats.gold] to row[DefaultStats.boost])
        }

        val combatMap = CombatStats
            .select(
                CombatStats.uuid,

                CombatStats.kill,
                CombatStats.killStreak,
                CombatStats.death,
                CombatStats.deathStreak
            )
            .associateBy { it[CombatStats.uuid] }

        defaultList.mapNotNull { (uuid, stats) ->
            val combatRow = combatMap[uuid]?: return@mapNotNull null

            RankingData(
                uuid = uuid.toUuid(),
                gold = stats.first,
                boost = stats.second,

                kill = combatRow[CombatStats.kill],
                killStreak = combatRow[CombatStats.killStreak],
                death = combatRow[CombatStats.death],
                deathStreak = combatRow[CombatStats.deathStreak]
            )
        }
    }
