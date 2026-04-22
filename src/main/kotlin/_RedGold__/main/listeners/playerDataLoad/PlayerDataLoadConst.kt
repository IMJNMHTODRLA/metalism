package _RedGold__.main.listeners.playerDataLoad

import _RedGold__.main.managers.playerData.DISCORD_INVITE
import _RedGold__.main.managers.playerData.PREFIX

object PlayerDataLoadConst {
    val PERM_BAN_MESSAGE = """
        $PREFIX
        
        &f&l당신은 이 서버에서 &4&l영구적으로 정지되었습니다.
        &7&l사유: %reason%
        
        &7&l항소를 하실려면 디스코드 서버에 접속 해 항소 티켓을 열어주세요.
        &7&l디스코드: &b&l$DISCORD_INVITE
        
        &8uuid: %uuid%
        &8bannedAt: %bannedAt%
    """.trimIndent()
    val TEMP_BAN_MESSAGE = """
        $PREFIX
        
        &f&l당신은 이 서버에서 &c&l%days%일 %hours%시간 %minutes%분 %seconds%초 동안 일시적으로 정지되었습니다.
        &7&l사유: %reason%
        
        &7&l항소를 하실려면 디스코드 서버에 접속 해 항소 티켓을 열어주세요.
        &7&l디스코드: &b&l$DISCORD_INVITE
        
        &8uuid: %uuid%
        &8bannedAt: %bannedAt%
    """.trimIndent()
}
