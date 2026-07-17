package _RedGold__.main.core.guild.chat

import _RedGold__.main.core.guild.joinedGuildCache
import _RedGold__.main.core.guild.policy.isAllowChatGuilds
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.TimeTool.now
import org.bukkit.Bukkit
import org.bukkit.entity.Player

fun sendGuildChat(player: Player, guildId: Int?, message: String) {
    val uuid = player.uniqueId

    if (guildId == null) {
        player.fail("&c길드에 접속이 안 되어 있습니다.")
        return
    }

    if (guildId !in isAllowChatGuilds) {
        player.fail("&f&l길드 채팅이 &c&l거부&f&l(으)로 되어 있습니다.")
        return
    }

    val cooldown = chatCooldownMap[uuid]?: 0L
    val remain = cooldown - now
    if (remain > 0) {
        player.fail("&c${remain}초 뒤에 메시지를 보낼 수 있습니다.")
        return
    }

    chatCooldownMap[uuid] = now + getChatCooldown(player)

    val guildMembers = joinedGuildCache
        .filter { it.value == guildId }
        .keys

    guildMembers.forEach {
        Bukkit.getPlayer(it)
            ?.good("&a&l[길드 챗] &e&l${player.name}&f: $message")
    }
}
