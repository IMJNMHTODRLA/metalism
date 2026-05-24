package _RedGold__.main.commands.user.boost.listeners.selectGui

import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.modify
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class SelectGui {
    fun openGui(player: Player) {
        val gui = SelectHolder().inventory
        gui.item(BACKGROUND)

        gui.item[11] = getItem(
            Material.BOOK,
            "&2&l후원 정보 확인하기"
        )

        gui.item[13] = getItem(
            Material.EMERALD,
            "&e&l후원 신청 하기"
        ).modify { enchantEffect() }

        gui.item[15] = getItem(
            Material.EMERALD_BLOCK,
            "&a&l후원 기능 설정하기"
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_NOTE_BLOCK_BASS)
    }
}