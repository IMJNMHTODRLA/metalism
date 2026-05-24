package _RedGold__.main.core.guild.settings.leader.policy

import _RedGold__.main.managers.database.tableManager.guildDB.GuildStats
import _RedGold__.main.managers.guildDB
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

data class PolicyData(
    val id: Int,
    var allowSearch: Boolean,
    var allowChat: Boolean,
    var enableWhitelist: Boolean,
    var allowPvp: Boolean,
)

fun getPolicyData(id: Int) = transaction(guildDB) {
    GuildStats
        .select(
            GuildStats.id,
            GuildStats.allowSearch,
            GuildStats.allowChat,
            GuildStats.enableWhitelist,
            GuildStats.allowPvp
        )
        .where { GuildStats.id eq id }
        .orderBy(GuildStats.id to SortOrder.ASC)
        .limit(1)
        .map {
            PolicyData(
                it[GuildStats.id],
                it[GuildStats.allowSearch],
                it[GuildStats.allowChat],
                it[GuildStats.enableWhitelist],
                it[GuildStats.allowPvp]
            )
        }
        .singleOrNull()
}

fun savePolicyData(data: PolicyData) = transaction(guildDB) {
    GuildStats
        .update({ GuildStats.id eq data.id }) {
            it[allowSearch] = data.allowSearch
            it[allowChat] = data.allowChat
            it[enableWhitelist] = data.enableWhitelist
            it[allowPvp] = data.allowPvp
        }
}
