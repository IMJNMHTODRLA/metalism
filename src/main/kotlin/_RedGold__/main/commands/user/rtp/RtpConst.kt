package _RedGold__.main.commands.user.rtp

import _RedGold__.main.functions.Color.sendAction
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.Color.sendTitleMsg
import org.bukkit.entity.Player

object RtpConst {
    const val COOLDOWN = 1800

    fun sendRtpMsg(player: Player, msg: String) {
        player.sendMsg(msg)
        player.sendAction(msg)
        player.sendTitleMsg("", msg, 0, 20, 0)
    }
}