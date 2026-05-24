package _RedGold__.main.core.guild.settings.leader.member.memberDetail

import _RedGold__.main.core.guild.getGuildId2Leader
import _RedGold__.main.core.guild.settings.leader.member.MemberData
import _RedGold__.main.core.guild.playerCooldownMsg
import _RedGold__.main.core.guild.sendNotLeaderMsg
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.task
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.Sound
import org.bukkit.entity.Player
import java.util.*

object MemberDetailGui {
    fun openGui(player: Player, name: String) {
        val target = Bukkit.getPlayer(name)?: Bukkit.getOfflinePlayer(name)
        openGui(player, target)
    }

    fun openGui(player: Player, uuid: UUID) {
        val target = Bukkit.getPlayer(uuid)?: Bukkit.getOfflinePlayer(uuid)
        openGui(player, target)
    }

    fun openGui(player: Player, target: OfflinePlayer) {
        if (playerCooldownMsg(player)) return
        val uuid = player.uniqueId
        val targetUUID = target.uniqueId

        taskAsync {
            val id = getGuildId2Leader(uuid)?: run {
                sendNotLeaderMsg(player)
                return@taskAsync
            }

            task {
                val gui = MemberDetailHolder(target, MemberData(id, targetUUID)).inventory
                gui.item(BACKGROUND)

                gui.item[12] = getItem(
                    Material.GOLD_BLOCK,
                    "&6&l리더 권한 이전",
                    "",
                    "&c&l주의! 리더 권한을 이전한 이후 취소가 불가능 합니다."
                )

                gui.item[13] = getItem(
                    Material.BARRIER,
                    "&4&l&n&o차단",
                    "",
                    "&c&l주의! 차단된 플레이어는 다시 길드 접속이 불가능하며 취소 또한 불가능 합니다."
                )

                gui.item[14] = getItem(
                    Material.RED_CONCRETE,
                    "&c&l추방",
                    "",
                    "&c&l주의! 추방된 플레이어는 다시 길드에 접속이 가능합니다."
                )

                player.inv + gui
                player.sendSound(Sound.UI_BUTTON_CLICK)
            }
        }
    }
}