package _RedGold__.main.managers.rankingManager

import _RedGold__.main.functions.ExceptionSeverity
import _RedGold__.main.functions.NumberFormat.toUuid
import _RedGold__.main.functions.catch
import _RedGold__.main.managers.database.tableManager.playersDB.CombatStats
import _RedGold__.main.managers.database.tableManager.playersDB.DefaultStats
import _RedGold__.main.managers.playerData.PlayerData
import _RedGold__.main.managers.playerData.dataManager.CombatData
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun getRanking() = catch("전체 랭킹 데이터 로드 실패", ExceptionSeverity.CRITICAL) {
    transaction {
        (DefaultStats innerJoin CombatStats)
            .select(
                DefaultStats.uuid,
                DefaultStats.gold,
                DefaultStats.boost,
                CombatStats.kill,
                CombatStats.killStreak,
                CombatStats.death,
                CombatStats.deathStreak
            )
            .map { row ->
                RankingData(
                    row[DefaultStats.uuid].toUuid(),
                    row[DefaultStats.gold],
                    row[DefaultStats.boost],
                    row[CombatStats.kill],
                    row[CombatStats.killStreak],
                    row[CombatStats.death],
                    row[CombatStats.deathStreak],
                )
            }
    }
}