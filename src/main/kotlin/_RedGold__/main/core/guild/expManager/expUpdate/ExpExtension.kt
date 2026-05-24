package _RedGold__.main.core.guild.expManager.expUpdate

import _RedGold__.main.core.guild.GuildStatsData
import _RedGold__.main.managers.database.tableManager.guildDB.GuildMembers
import _RedGold__.main.managers.database.tableManager.guildDB.GuildStats
import _RedGold__.main.managers.database.tableManager.guildDB.guildBan.GuildBans
import _RedGold__.main.managers.guildDB
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun allGetGuildStats() =
    transaction(guildDB) {
        val memberCountExpression = GuildMembers.guildId.count().alias("member_count")
        val memberSubQuery = GuildMembers
            .select(GuildMembers.guildId, memberCountExpression)
            .groupBy(GuildMembers.guildId)
            .alias("members_sub")

        val banCountExpression = GuildBans.guildId.count().alias("ban_count")
        val banSubQuery = GuildBans
            .select(GuildBans.guildId, banCountExpression)
            .groupBy(GuildBans.guildId)
            .alias("bans_sub")

        GuildStats
            .join(memberSubQuery, JoinType.LEFT, GuildStats.id, memberSubQuery[GuildMembers.guildId])
            .join(banSubQuery, JoinType.LEFT, GuildStats.id, banSubQuery[GuildBans.guildId])
            .selectAll()
            .map { row ->
                val totalMembers = row[memberSubQuery[memberCountExpression]]
                val totalBans = row[banSubQuery[banCountExpression]]

                GuildStatsData(
                    row[GuildStats.id],

                    row[GuildStats.name],
                    row[GuildStats.leader],
                    row[GuildStats.exp],

                    row[GuildStats.allowSearch],
                    row[GuildStats.allowChat],
                    row[GuildStats.enableWhitelist],
                    row[GuildStats.allowPvp],

                    row[GuildStats.createdAt],

                    totalMembers,
                    totalBans
                )
            }
    }