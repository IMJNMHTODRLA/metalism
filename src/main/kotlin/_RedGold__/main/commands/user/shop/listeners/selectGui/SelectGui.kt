package _RedGold__.main.commands.user.shop.listeners.selectGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class SelectGui {
    fun openGui(player: Player) {
        val gui = SelectHolder().inventory
        gui.item(BACKGROUND)

        gui.item[11] = getItem(
            Material.GOLD_INGOT,
            "&6&l골드 상점"
        )

        gui.item[12] = getItem(
            Material.DIAMOND,
            "&b&l크리스탈 상점"
        )

        gui.item[13] = getItem(
            Material.PLAYER_HEAD,
            "&a&l유저 상점"
        )

        gui.item[14] = getItem(
            Material.EXPERIENCE_BOTTLE,
            "&e&l일일 상점"
        )

        gui.item[15] = getItem(
            Material.ENCHANTED_GOLDEN_APPLE,
            "&b&l월간 상점"
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_CHEST_OPEN)
    }
}