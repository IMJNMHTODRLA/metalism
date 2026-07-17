package _RedGold__.main.core.guild.search

import _RedGold__.main.managers.database.tableManager.guildDB.GuildStats
import _RedGold__.main.managers.guildDB
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.transactions.transaction

data class GuildInfoData(
    val id: Int,
    val name: String,
)

fun getGuildInfoList(start: Int, limit: Int, keyword: String?) =
    transaction(guildDB) {
        val query = GuildStats
            .select(GuildStats.id, GuildStats.name, GuildStats.allowSearch)
            .where { GuildStats.allowSearch eq true }
            .orderBy(GuildStats.id to SortOrder.ASC)

        if (!keyword.isNullOrBlank()) {
            query.andWhere { GuildStats.name like "%$keyword%" }
        }

        query
            .offset(start.toLong())
            .limit(limit)

            .map {
                GuildInfoData(
                    it[GuildStats.id],
                    it[GuildStats.name]
                )
            }
    }
