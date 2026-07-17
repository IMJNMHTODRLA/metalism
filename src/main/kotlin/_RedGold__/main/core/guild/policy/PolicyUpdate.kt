package _RedGold__.main.core.guild.policy

import _RedGold__.main.core.guild.joinedGuildCache
import _RedGold__.main.core.guild.updateTick
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.loads.SetSlowInit
import _RedGold__.main.managers.database.tableManager.guildDB.GuildStats
import _RedGold__.main.managers.guildDB
import org.jetbrains.exposed.sql.transactions.transaction

private data class PolicyUpdateData(
    val allowSearch: Set<Int>,
    val allowChat: Set<Int>,
    val enableWhitelist: Set<Int>,
    val allowPvp: Set<Int>
)

@SetSlowInit
object PolicyUpdate {
    @SetSlowInit
    fun startUpdateTask() {
        taskAsync(loop = updateTick) {
            val guildCacheData = joinedGuildCache.values.distinct()
            val result = getAllAllowSearch(guildCacheData)

            isAllowSearchGuilds = result.allowSearch
            isAllowChatGuilds = result.allowChat
            isEnableWhitelistGuilds = result.enableWhitelist
            isAllowPvpGuilds = result.allowPvp
        }
    }

    private fun getAllAllowSearch(ids: List<Int>) =
        transaction(guildDB) {
            val allowSearch = mutableSetOf<Int>()
            val allowChat = mutableSetOf<Int>()
            val enableWhitelist = mutableSetOf<Int>()
            val allowPvp = mutableSetOf<Int>()

            GuildStats
                .select(
                    GuildStats.id,
                    GuildStats.allowSearch,
                    GuildStats.allowChat,
                    GuildStats.enableWhitelist,
                    GuildStats.allowPvp
                )
                .where { GuildStats.id inList ids }
                .forEach {
                    val id = it[GuildStats.id]

                    if (it[GuildStats.allowSearch]) allowSearch.add(id)
                    if (it[GuildStats.allowChat]) allowChat.add(id)
                    if (it[GuildStats.enableWhitelist]) enableWhitelist.add(id)
                    if (it[GuildStats.allowPvp]) allowPvp.add(id)
                }

            PolicyUpdateData(allowSearch, allowChat, enableWhitelist, allowPvp)
        }
}