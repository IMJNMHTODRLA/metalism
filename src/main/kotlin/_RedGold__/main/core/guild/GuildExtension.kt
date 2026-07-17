package _RedGold__.main.core.guild

import _RedGold__.main.managers.database.tableManager.guildDB.GuildMembers
import _RedGold__.main.managers.guildDB
import org.jetbrains.exposed.sql.transactions.transaction

fun allGetGuildMembers() =
    transaction(guildDB) {
        GuildMembers
            .select(
                GuildMembers.guildId,
                GuildMembers.uuid
            )
            .associate { row ->
                row[GuildMembers.uuid] to row[GuildMembers.guildId]
            }
    }
