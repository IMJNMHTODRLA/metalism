package _RedGold__.main.managers.banManager

import _RedGold__.main.managers.banDB
import _RedGold__.main.managers.database.tableManager.banDB.BanStats
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.upsert
import java.util.*

fun getBan(uuid: UUID): BanData? {
    return transaction(banDB) {
        BanStats.selectAll()
            .where { BanStats.uuid eq uuid.toString() }
            .singleOrNull()
            ?.let { row ->
                BanData(
                    uuid,
                    row[BanStats.type],
                    row[BanStats.reason],
                    row[BanStats.expiresAt],
                    row[BanStats.bannedAt]
                )
            }
    }
}

fun saveBan(data: BanData) {
    transaction(banDB) {
        BanStats.upsert {
            it[uuid] = data.uuid.toString()
            it[type] = data.type
            it[reason] = data.reason
            it[expiresAt] = data.expiresAt
            it[bannedAt] = data.bannedAt
        }
    }
}

fun removeBan(playerUUID: UUID) {
    transaction(banDB) {
        BanStats.deleteWhere { uuid eq playerUUID.toString() }
    }
}
