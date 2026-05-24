package _RedGold__.main.core.guild

import _RedGold__.main.managers.database.tableManager.guildDB.GuildMembers
import _RedGold__.main.managers.guildDB
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun allGetGuildMembers(): Map<UUID, Int> =
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
