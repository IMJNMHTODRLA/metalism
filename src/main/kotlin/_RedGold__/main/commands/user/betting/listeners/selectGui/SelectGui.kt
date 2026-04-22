package _RedGold__.main.commands.user.betting.listeners.selectGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class SelectGui {
    fun openGui(player: Player) {
        val gui = SelectHolder().inventory
        gui.item(BACKGROUND)

        gui.item[10] = getItem(
            Material.GOLD_INGOT,
            "&6&l동전 던지기"
        )

        gui.item[12] = getItem(
            Material.OBSERVER,
            "&7&l주사위 굴리기"
        )

        gui.item[14] = getItem(
            Material.EXPERIENCE_BOTTLE,
            "&b&lHIGH&8&l\\&&c&lLOW"
        )

        gui.item[16] = getItem(
            Material.DIAMOND,
            "&e&l로또 추첨"
        )

        player.openInventory(gui)
        player.sendSound(Sound.BLOCK_CHEST_OPEN)
    }
}