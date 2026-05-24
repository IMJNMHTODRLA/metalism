package _RedGold__.main.core.guild.settings.leader.whitelist

import _RedGold__.main.managers.database.tableManager.guildDB.GuildWhitelists
import _RedGold__.main.managers.guildDB
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

data class WhitelistData(
    val id: Int,
    val players: List<UUID>
) {
    constructor(id: Int, vararg players: UUID) : this(id, players.toList())
}

fun getWhitelistData(id: Int, page: Int): WhitelistData {
    return transaction(guildDB) {
        val players = GuildWhitelists
            .select(GuildWhitelists.uuid)
            .where { GuildWhitelists.guildId eq id }
            .orderBy(GuildWhitelists.guildId to SortOrder.ASC)
            .limit(28)
            .offset(page * 28L)
            .map { it[GuildWhitelists.uuid] }

        WhitelistData(id, players)
    }
}

fun addWhitelistData(data: WhitelistData) =
    transaction(guildDB) {
        GuildWhitelists.batchInsert(data.players, true) { uuid ->
            this[GuildWhitelists.guildId] = data.id
            this[GuildWhitelists.uuid] = uuid
        }
    }

fun removeWhitelistData(data: WhitelistData) =
    transaction(guildDB) {
        GuildWhitelists.deleteWhere {
            (guildId eq data.id) and
            (uuid inList data.players)
        }
    }
