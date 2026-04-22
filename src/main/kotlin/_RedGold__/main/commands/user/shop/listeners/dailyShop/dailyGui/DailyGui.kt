package _RedGold__.main.commands.user.shop.listeners.dailyShop.dailyGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class DailyGui {
    fun openGui(player: Player) {
        val gui = DailyHolder().inventory
        gui.item(BACKGROUND)

        gui.item[12] = getItem(Material.GOLD_INGOT, "&6&l골드 &f&l일일 상점")

        gui.item[14] = getItem(Material.END_CRYSTAL, "&b&l크리스탈 &f&l일일 상점")

        player.inv + gui
        player.sendSound(Sound.BLOCK_NOTE_BLOCK_PLING)
    }
}