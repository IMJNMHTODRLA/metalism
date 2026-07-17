package _RedGold__.main.core.guild.join.inviteCode

import _RedGold__.main.core.guild.join.joinGuildWithId
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.TimeTool.now
import org.bukkit.entity.Player
import java.util.UUID

fun addInviteCode(guildId: Int): Pair<UUID, InviteData> {
    val inviteCode = UUID.randomUUID()
    val metadata = InviteData(
        guildId,
        now + IC_EXP_TIME
    )

    inviteCodeMap[inviteCode] = metadata
    return inviteCode to metadata
}

fun verifyInviteCode(code: UUID): Pair<InviteData?, String> {
    val inviteData = inviteCodeMap.remove(code)
        ?: return null to "&4&l존재하지 않는 초대 코드 입니다."

    if (inviteData.isExpired) {
        return null to "&c&l만료된 초대 코드 입니다."
    }

    return inviteData to "&a&l초대 코드 사용에 성공 하였습니다."
}

fun joinGuildWithCode(code: UUID, player: Player) {
    val (data, message) = verifyInviteCode(code)
    if (data == null) {
        player.fail(message)
        return
    }

    joinGuildWithId(data.guildId, player)
}