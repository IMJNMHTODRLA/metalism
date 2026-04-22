package _RedGold__.main.functions

import _RedGold__.main.functions.Gui.sendSound
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object Color {
    private val COLOR_CODE_REGEX = Regex("(?<!\\\\)&")

    fun rgb(rgb: String): String {
        val result = StringBuilder("§x")
        for (c in rgb.toCharArray()) result.append("§").append(c)
        return result.toString()
    }

    fun String.rgb(): String = rgb(this)

    fun gc(msg: String): String = msg.replace(COLOR_CODE_REGEX, "§").replace("\\&", "&")
    fun String.gc(): String = gc(this)
    fun Player.sendMsg(msg: String) = sendMessage(gc(msg))
    fun CommandSender.sendMsg(msg: String) = sendMessage(gc(msg))
    fun String.broadcast() = Bukkit.broadcastMessage(gc(this))
    fun Component.broadcast() = Bukkit.broadcast(this)

    fun Player.sendAction(msg: String) = sendActionBar(gc(msg))
    fun Player.sendTitleMsg(
        main: String, sub: String = "", i: Int = 0, i1: Int = 20, i2: Int = 0
    ) = sendTitle(gc(main), gc(sub), i, i1, i2)

    fun Player.fail(msg: String) {
        sendMsg(msg)
        sendSound(Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f)
    }

    fun Player.good(msg: String) {
        sendMsg(msg)
        sendSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
    }
}