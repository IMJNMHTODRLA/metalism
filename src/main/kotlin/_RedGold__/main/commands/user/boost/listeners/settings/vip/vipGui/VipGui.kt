package _RedGold__.main.commands.user.boost.listeners.settings.vip.vipGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class VipGui {
    fun openGui(player: Player) {
        val gui = VipHolder().inventory
        gui.item(BACKGROUND)

        gui.item[12] = getItem(
            Material.BOOK,
            "&e&lGG 메시지 색깔 설정"
        )

        gui.item[13] = getItem(
            Material.BOOK,
            "&c&l공격 파티클 설정"
        )

        gui.item[14] = getItem(
            Material.BOOK,
            "&4&l피해 파티클 설정"
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_CHEST_OPEN)
    }
}