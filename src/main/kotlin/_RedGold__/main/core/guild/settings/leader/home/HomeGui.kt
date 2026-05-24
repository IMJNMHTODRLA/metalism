package _RedGold__.main.core.guild.settings.leader.home

import _RedGold__.main.core.guild.getGuildId2Leader
import _RedGold__.main.core.guild.playerCooldownMsg
import _RedGold__.main.core.guild.sendNotLeaderMsg
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

object HomeGui {
    fun openGui(player: Player) {
        if (playerCooldownMsg(player)) return
        val uuid = player.uniqueId

        taskAsync {
            val id = getGuildId2Leader(uuid)?: run {
                sendNotLeaderMsg(player)
                return@taskAsync
            }

            task {
                val gui = HomeHolder(id).inventory
                gui.item(BACKGROUND)

                gui.item[13] = getItem(
                    Material.RED_BED,
                    "&f&l클릭 시 &e&l현 위치&f&l로 홈이 &a&l설정됩니다.",
                    "",
                    "&7&l오직 오버월드에서만 설정이 가능합니다."
                )

                player.inv + gui
                player.sendSound(Sound.UI_BUTTON_CLICK)
            }
        }
    }
}