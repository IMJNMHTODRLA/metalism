package _RedGold__.main.core.guild.settings.member.leave

import _RedGold__.main.core.guild.joinedGuildCache
import _RedGold__.main.managers.database.tableManager.guildDB.GuildMembers
import _RedGold__.main.managers.guildDB
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun leaveGuild(uuid: UUID) =
    transaction(guildDB) {
        GuildMembers.deleteWhere {
            GuildMembers.uuid eq uuid
        }

        joinedGuildCache.remove(uuid)
    }
