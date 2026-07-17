package _RedGold__.main.functions

import _RedGold__.main.functions.Gui.sendSound
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object Color {
    //private val COLOR_CODE_REGEX = Regex("(?<!\\\\)&")

    fun rgb(rgb: String): String {
        val result = StringBuilder("§x")
        for (c in rgb.toCharArray()) result.append("§").append(c)
        return result.toString()
    }

    @JvmName("amazingRgb")
    fun String.rgb(): String = rgb(this)

    fun gc(msg: String) =
        buildString(msg.length) {
            var i = 0
            while (i < msg.length) {
                val char = msg[i]
                if (char == '\\' && msg.getOrNull(i + 1) == '&') {
                    append('&')
                    i += 2
                } else {
                    append(if (char == '&') '§' else char)
                    i++
                }
            }
        }

    @JvmName("amazingGc")
    fun String.gc() = gc(this)
    fun Player.sendMsg(msg: String) = sendMessage(msg.gc())
    fun CommandSender.sendMsg(msg: String) = sendMessage(msg.gc())
    fun String.broadcast() = Bukkit.broadcastMessage(gc())
    fun Component.broadcast() = Bukkit.broadcast(this)

    fun Player.sendAction(msg: String) = sendActionBar(msg.gc())
    fun Player.sendTitleMsg(
        main: String, sub: String = "", i: Int = 0, i1: Int = 20, i2: Int = 0
    ) = sendTitle(main.gc(), sub.gc(), i, i1, i2)

    fun Player.fail(msg: String) {
        sendMsg(msg)
        sendSound(Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f)
    }

    fun Player.good(msg: String) {
        sendMsg(msg)
        sendSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
    }
}