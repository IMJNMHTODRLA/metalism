package _RedGold__.main.core.guild.settings.member.leave

import _RedGold__.main.core.guild.isGuildJoin
import _RedGold__.main.core.guild.isLeader
import _RedGold__.main.core.guild.playerCooldownMsg
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

object LeaveGui {
    fun openGui(player: Player) {
        if (playerCooldownMsg(player)) return
        val uuid = player.uniqueId

        taskAsync {
            if (isLeader(uuid)) return@taskAsync
            if (!isGuildJoin(player)) return@taskAsync

            task {
                val gui = LeaveHolder().inventory
                gui.item(BACKGROUND)

                gui.item[13] = getItem(
                    Material.BARRIER,
                    "&f&l길드 탈퇴를 원할 경우 &c&l3번 더 클릭해주세요.",
                    "",
                    "&e&l길드 탈퇴 시 다시 가입 가능합니다,"
                )

                player.inv + gui
                player.sendSound(Sound.UI_BUTTON_CLICK)
            }
        }
    }
}