package _RedGold__.main.core.guild.join

import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.managers.database.tableManager.guildDB.GuildMembers
import _RedGold__.main.managers.database.tableManager.guildDB.GuildWhitelists
import _RedGold__.main.managers.database.tableManager.guildDB.guildBan.GuildBanType
import _RedGold__.main.managers.database.tableManager.guildDB.guildBan.GuildBans
import _RedGold__.main.managers.guildDB
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.upsert
import java.util.*

fun setJoinGuildDatabase(id: Int, target: UUID) =
    transaction(guildDB) {
        try {
            guildJoinLocked.add(id)

            GuildMembers.upsert {
                it[guildId] = id
                it[uuid] = target
                it[joinedAt] = now
            }
        } finally {
            guildJoinLocked.remove(id)
        }
    }

fun playerGuildBanData(id: Int, target: UUID): GuildBanType? =
    transaction(guildDB) {
        GuildBans
            .selectAll()
            .where {
                (GuildBans.guildId eq id) and
                (GuildBans.uuid eq target)
            }
            .map {
                it[GuildBans.reason]
            }
            .singleOrNull()
    }

fun playerGuildInWhitelist(id: Int, target: UUID): Boolean =
    transaction(guildDB) {
        !GuildWhitelists
            .selectAll()
            .where {
                (GuildWhitelists.guildId eq id) and
                (GuildWhitelists.uuid eq target)
            }
            .empty()
    }
