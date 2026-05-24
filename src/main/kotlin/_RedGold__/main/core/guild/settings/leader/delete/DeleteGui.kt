package _RedGold__.main.core.guild.settings.leader.delete

import _RedGold__.main.core.guild.getGuildId2Leader
import _RedGold__.main.core.guild.playerCooldownMsg
import _RedGold__.main.core.guild.sendNotLeaderMsg
import _RedGold__.main.core.guild.totalGuildMembers
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.task
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

object DeleteGui {
    fun openGui(player: Player) {
        if (playerCooldownMsg(player)) return
        val uuid = player.uniqueId

        taskAsync {
            val id = getGuildId2Leader(uuid)?: run {
                sendNotLeaderMsg(player)
                return@taskAsync
            }

            if (totalGuildMembers(id) > 0) {
                player.fail("&c&l길드 맴버 수가 1명 이상입니다.")
                return@taskAsync
            }

            task {
                val gui = DeleteHolder(id).inventory
                gui.item(BACKGROUND)

                gui.item[13] = getItem(
                    Material.BARRIER,
                    "&f&l길드 삭제를 원할 경우 &c&l3번 더 클릭해주세요.",
                    "",
                    "&e&l길드 삭제 시, 길드의 혜택 및 EXP 등 모든 것들이 &c&l초기화 되며 더 이상 사용이 불가능 합니다."
                )

                player.inv + gui
                player.sendSound(Sound.UI_BUTTON_CLICK)
            }
        }
    }
}