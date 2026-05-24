package _RedGold__.main.core.guild

import _RedGold__.main.managers.database.tableManager.guildDB.GuildMembers
import _RedGold__.main.managers.database.tableManager.guildDB.GuildStats
import _RedGold__.main.managers.database.tableManager.guildDB.guildBan.GuildBans
import _RedGold__.main.managers.guildDB
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

data class GuildStatsData(
    val id: Int,

    val name: String,
    val leader: UUID,
    val exp: Long,

    val allowSearch: Boolean,
    val allowChat: Boolean,
    val enableWhitelist: Boolean,
    val allowPvp: Boolean,

    val createdAt: Long,

    val totalMembers: Long,
    val totalBans: Long
)

fun getGuildStats(id: Int) =
    transaction(guildDB) {
        val statsRow = GuildStats
            .selectAll()
            .where { GuildStats.id eq id }
            .singleOrNull()?: return@transaction null

        val totalMembers = GuildMembers
            .selectAll()
            .where { GuildMembers.guildId eq id }
            .count()

        val totalBans = GuildBans
            .selectAll()
            .where { GuildBans.guildId eq id }
            .count()

        GuildStatsData(
            id,
            statsRow[GuildStats.name],
            statsRow[GuildStats.leader],
            statsRow[GuildStats.exp],

            statsRow[GuildStats.allowSearch],
            statsRow[GuildStats.allowChat],
            statsRow[GuildStats.enableWhitelist],
            statsRow[GuildStats.allowPvp],

            statsRow[GuildStats.createdAt],

            totalMembers,
            totalBans
        )
    }
