package _RedGold__.main.commands.user.boost.listeners.info.infoGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class InfoGui {
    fun openGui(player: Player) {
        val gui = InfoHolder().inventory
        gui.item(BACKGROUND)

        gui.item[11] = getItem(
            Material.DIAMOND,
            "&b&l크리스탈 상품"
        )

        gui.item[13] = getItem(
            Material.NAME_TAG,
            "&6&l랭크 상품"
        )

        gui.item[15] = getItem(
            Material.CHEST,
            "&e&l패키지 상품"
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_CHEST_OPEN)
    }
}