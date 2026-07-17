package _RedGold__.main.core.guild.register

import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.managers.database.tableManager.guildDB.GuildMembers
import _RedGold__.main.managers.database.tableManager.guildDB.GuildStats
import _RedGold__.main.managers.guildDB
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

private fun insertGuildMembers(id: Int, leader: UUID) =
    GuildMembers.insert {
        it[guildId] = id
        it[uuid] = leader
        it[joinedAt] = now
    }

private fun insertGuildStats(guildName: String, guildLeader: UUID): Int {
    val maxId = GuildStats
        .select(GuildStats.id)
        .orderBy(GuildStats.id to SortOrder.DESC)
        .limit(1)
        .map { it[GuildStats.id] }
        .singleOrNull() ?: 0

    val nextId = maxId + 1

    GuildStats.insert {
        it[id] = nextId

        it[name] = guildName

        it[leader] = guildLeader
        it[exp] = 0L

        it[allowSearch] = true
        it[allowChat] = true
        it[enableWhitelist] = true
        it[allowPvp] = true

        it[createdAt] = now
    }[GuildStats.id]

    return nextId
}

fun insertGuild(name: String, leader: UUID): Int =
    transaction(guildDB) {
        val id = insertGuildStats(name, leader)
        insertGuildMembers(id, leader)

        id
    }