package _RedGold__.main.core.guild.expManager

import _RedGold__.main.core.guild.getGuildId2Member
import _RedGold__.main.core.guild.getGuildStats
import _RedGold__.main.core.guild.joinedGuildCache
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.loads.RequireListener
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

@RequireListener
class GuildCacheListener : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val uuid = event.player.uniqueId
        taskAsync {
            val guildId = joinedGuildCache[uuid]
                ?: getGuildId2Member(uuid)
                ?: return@taskAsync

            guildLevelCache[guildId]
                ?: getGuildStats(guildId)
                    ?.also { guildLevelCache[guildId] = currentLevel(it.exp) }
        }
    }
}
