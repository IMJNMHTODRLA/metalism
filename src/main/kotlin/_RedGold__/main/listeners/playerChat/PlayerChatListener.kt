package _RedGold__.main.listeners.playerChat

import _RedGold__.main.functions.Color.gc
import _RedGold__.main.listeners.GlobalValue
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.playerData.data
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

@RequireListener
class PlayerChatListener : Listener {
    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {
        val player = event.player
        val name = player.name
        val uuid = player.uniqueId

        val message = event.message
        val messageColor = PlayerChatConst.getGGColor(player, message, GlobalValue.chatGGTiming[uuid])

        val prefix = PermissionEnum[player].prefix
        val (_, style) = player.data.equipStyle

        event.format = "$style$prefix $name&f: $messageColor$message".gc()
    }
}