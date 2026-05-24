package _RedGold__.main.core.guild

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.functions.afterWith
import _RedGold__.main.functions.remainingWith
import org.bukkit.entity.Player
import java.util.*

private val dbCooldownMap = mutableMapOf<UUID, Long>()
private const val COOLDOWN = 1

fun useDB(uuid: UUID) { dbCooldownMap[uuid] = now }
fun hasCooldown(uuid: UUID): Boolean {
    val cooldown = dbCooldownMap[uuid]?: 0
    return !cooldown.afterWith(COOLDOWN)
}

fun playerCooldownMsg(player: Player): Boolean {
    val uuid = player.uniqueId
    val cooldown = dbCooldownMap[uuid]?: 0
    if (hasCooldown(uuid)) {
        player.sendMsg("&c${cooldown.remainingWith(COOLDOWN)}초 후에 다시 시도해주세요.")
        return true
    }

    useDB(uuid)
    return false
}
