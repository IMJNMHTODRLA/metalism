package _RedGold__.main.commands.user.shop.listeners.goldShop.goldGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class GoldGui {
    fun openGui(player: Player) {
        val gui = GoldHolder().inventory
        gui.item(BACKGROUND)

        gui.item[4] = getItem(Material.ENCHANTED_BOOK, "&f&l인첸트 북 상점")

        gui.item[12] = getItem(Material.GOLDEN_CARROT, "&f&l음식 상점")
        gui.item[13] = getItem(Material.END_CRYSTAL, "&d&lCPVP 상점")
        gui.item[14] = getItem(Material.IRON_INGOT, "&f&l광물 상점")

        gui.item[22] = getItem(Material.WHEAT, "&f&l농작물 상점")

        player.inv + gui
        player.sendSound(Sound.BLOCK_NOTE_BLOCK_PLING)
    }
}