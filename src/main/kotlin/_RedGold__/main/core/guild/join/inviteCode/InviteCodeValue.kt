package _RedGold__.main.core.guild.join.inviteCode

import _RedGold__.main.functions.TimeTool.now
import java.util.*
import java.util.concurrent.ConcurrentHashMap

private typealias Code = UUID

data class InviteData(
    val guildId: Int,
    val expAt: Long
) {
    val isExpired
        get() = expAt - now <= 0L
}

val inviteCodeMap = ConcurrentHashMap<Code, InviteData>()
