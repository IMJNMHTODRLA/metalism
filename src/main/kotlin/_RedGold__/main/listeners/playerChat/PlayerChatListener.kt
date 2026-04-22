package _RedGold__.main.listeners.playerChat

import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.EasyPermission.permission
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.listeners.GlobalValue
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.variableManager.BoostSettingEnum
import _RedGold__.main.managers.playerData.variableManager.CHAT_GG_COLOR_MAP
import _RedGold__.main.managers.playerData.variableManager.cosmeticManager.CosmeticEnum
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChatEvent
import java.util.*

@RequireListener
class PlayerChatListener : Listener {
    @EventHandler
    fun onChat(event: PlayerChatEvent) {
        val player = event.player
        val uuid = player.uniqueId
        val message = event.message
        val chatGGColor = GlobalValue.chatGGTiming[uuid]?: (UUID.randomUUID() to 0L)
        var messageColor = "&f"
        val style = player.data.equipStyle.let {
            if (it.first) "${it.second} "
            else ""
        }

        if (
            player.permission(PermissionEnum.VIP) &&
            message.lowercase() in PlayerChatConst.VERIFY_WORDS &&
            chatGGColor.second > now
        ) {
            val sendPlayer = chatGGColor.first.let {
                Bukkit.getPlayer(it)
            }
            sendPlayer?.sendSound(Sound.ITEM_GOAT_HORN_SOUND_1)

            val playerChatGGColor = player.data.boostSettingMap[BoostSettingEnum.CHAT_GG_COLOR]?: 0
            messageColor = CHAT_GG_COLOR_MAP[playerChatGGColor]
            player.sendSound(Sound.UI_TOAST_CHALLENGE_COMPLETE)

            GlobalValue.chatGGTiming.remove(uuid)
        }

        event.format = "$style${PermissionEnum[player].prefix} ${player.name}&f: $messageColor$message".gc()
    }
}