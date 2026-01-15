package _RedGold__.main.function

import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import org.bukkit.Sound
import org.bukkit.entity.Player

object Color {
    fun rgb(rgb: String): String {
        val result = StringBuilder("§x")
        for (c in rgb.toCharArray()) result.append("§").append(c)
        return result.toString()
    }

    fun gc(msg: String): String {
        return msg.replace(Regex("(?<!\\\\)&"), "§").replace("\\&", "&")
    }

    fun Player.fail(msg: String) {
        this.sendMessage(gc(msg))
        this.playSound(this.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
    }

    fun Player.good(msg: String) {
        this.sendMessage(gc(msg))
        this.playSound(this.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
    }
}