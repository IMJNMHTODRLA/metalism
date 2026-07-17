package _RedGold__.main.core.guild.join

import _RedGold__.main.core.guild.join.inviteCode.joinGuildWithCode
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.NumberFormat.toUuidOrNull
import _RedGold__.main.functions.TimeTool.now
import org.bukkit.entity.Player

fun joinGuild(player: Player, type: String?, sub: String?): Boolean {
    val uuid = player.uniqueId

    val lastUseJoin = guildJoinCooldown[uuid]?: 0L
    val remain = lastUseJoin - now
    if (remain > 0L) {
        player.fail("&c${remain}초 뒤에 길드 가입 기능을 사용 할 수 있습니다.")
        return true
    }

    when (type) {
        "초대코드" -> {
            val code = sub?.toUuidOrNull()?: return false
            joinGuildWithCode(code, player)
            return true
        }

        "길드ID" -> {
            val guildId = sub?.toIntOrNull()?: return false
            joinGuildWithId(guildId, player)
            return true
        }

        else -> return false
    }
}