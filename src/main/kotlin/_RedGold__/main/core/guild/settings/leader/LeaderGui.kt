package _RedGold__.main.core.guild.settings.leader

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

object LeaderGui {
    fun openGui(player: Player) {
        if (playerCooldownMsg(player)) return

        taskAsync {
            if (!isLeader(player)) return@taskAsync

            task {
                val gui = LeaderHolder().inventory
                gui.item(BACKGROUND)

                gui.item[10] = getItem(Material.BOOK, "&e&l정책 설정")
                gui.item[11] = getItem(Material.NAME_TAG, "&e&l화이트 리스트 설정")
                gui.item[12] = getItem(Material.PLAYER_HEAD, "&e&l길드원 설정")
                gui.item[13] = getItem(Material.RED_CONCRETE, "&c&l길드 삭제", "", "&c&l길드에 리더 한 명만이 남았을 때 길드 삭제가 가능합니다.")
                gui.item[14] = getItem(Material.RED_BED, "&c&l길드 홈 설정")

                player.inv + gui
                player.sendSound(Sound.UI_BUTTON_CLICK)
            }
        }
    }
}