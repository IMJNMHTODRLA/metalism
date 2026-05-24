package _RedGold__.main.core.guild.settings.leader.delete

import _RedGold__.main.core.guild.expManager.guildDelayAddExp
import _RedGold__.main.core.guild.expManager.guildLevelCache
import _RedGold__.main.core.guild.joinedGuildCache
import _RedGold__.main.managers.database.tableManager.guildDB.GuildStats
import _RedGold__.main.managers.guildDB
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction

fun deleteGuild(id: Int) =
    transaction(guildDB) {
        GuildStats.deleteWhere {
            GuildStats.id eq id
        }

        joinedGuildCache.values.remove(id)
        guildLevelCache.remove(id)
        guildDelayAddExp.remove(id)
    }