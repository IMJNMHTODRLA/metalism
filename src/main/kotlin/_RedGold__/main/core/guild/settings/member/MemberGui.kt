package _RedGold__.main.core.guild.settings.member

import _RedGold__.main.core.guild.isGuildJoin
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

object MemberGui {
    fun openGui(player: Player) {
        if (playerCooldownMsg(player)) return

        taskAsync {
            if (!isGuildJoin(player)) return@taskAsync

            task {
                val gui = MemberHolder().inventory
                gui.item(BACKGROUND)

                gui.item[10] = getItem(Material.GOLD_BLOCK, "&a&l[길드 기부] &8&l골드를 기부하여 EXP를 증가시킵니다.")
                gui.item[11] = getItem(Material.BOOK, "&e&l길드 통계")
                gui.item[12] = getItem(Material.CHEST, "&e&l길드 공용 창고", "", "&c&l주의: 공용 창고 관련된 로그는 남겨지지 않습니다.")
                gui.item[13] = getItem(Material.RED_BED, "&e&l길드 홈", "", "&e&l클릭 시 길드 홈으로 순간이동 됩니다.")
                gui.item[14] = getItem(Material.BARRIER, "&c&l탈퇴", "", "&c&l리더는 길드 탈퇴를 못 합니다.")

                player.inv + gui
                player.sendSound(Sound.UI_BUTTON_CLICK)
            }
        }
    }
}