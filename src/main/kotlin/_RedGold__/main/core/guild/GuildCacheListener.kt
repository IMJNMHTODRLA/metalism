package _RedGold__.main.core.guild

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
            joinedGuildCache[uuid]?:
                getGuildId2Member(uuid)
                    ?.also { joinedGuildCache[uuid] = it }
        }
    }
}
