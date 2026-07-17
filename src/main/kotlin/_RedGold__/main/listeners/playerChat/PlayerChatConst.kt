package _RedGold__.main.listeners.playerChat

import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.functions.isNull
import _RedGold__.main.listeners.GlobalValue
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.variableManager.boostSettingManager.BoostSettingEnum
import _RedGold__.main.managers.playerData.variableManager.boostSettingManager.chatGGColor.chatGGColorList
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import java.util.*

object PlayerChatConst {
    private val verifyWords = setOf("gg", "l", "ez", "ㅋ", "gl")

    fun getGGColor(player: Player, message: String, ggTiming: Pair<UUID, Long>?): String {
        if (ggTiming.isNull()) return "&f"

        if (
            player !in BoostSettingEnum.CHAT_GG_COLOR ||
            message.lowercase() !in verifyWords ||
            ggTiming.second <= now
        ) return "&f"

        GlobalValue.chatGGTiming.remove(player.uniqueId)

        Bukkit.getPlayer(ggTiming.first)?.sendSound(Sound.ITEM_GOAT_HORN_SOUND_1)
        player.sendSound(Sound.UI_TOAST_CHALLENGE_COMPLETE)

        val ggColorNum = player.data.boostSettingMap[BoostSettingEnum.CHAT_GG_COLOR]?: 0
        return chatGGColorList[ggColorNum].second
    }
}