package _RedGold__.main.core.guild.settings.leader.inviteCode

import _RedGold__.main.core.guild.getGuildId2Leader
import _RedGold__.main.core.guild.join.inviteCode.addInviteCode
import _RedGold__.main.core.guild.sendNotLeaderMsg
import _RedGold__.main.functions.smartMessage
import _RedGold__.main.functions.taskAsync
import org.bukkit.entity.Player

fun genInviteCode(player: Player) {
    player.closeInventory()

    taskAsync {
        val uuid = player.uniqueId

        val id = getGuildId2Leader(uuid)?: run {
            sendNotLeaderMsg(player)
            return@taskAsync
        }

        val (code, _) = addInviteCode(id)

        player.smartMessage {
            text("&f&l초대코드: &8[&b&l$code&8]") {
                hover("&8&l클릭 시 초대 코드를 복사합니다.")
                copy(code.toString())
            }

            text("\n&8초대 코드를 클릭 시 복사가 됩니다.")
        }
    }
}