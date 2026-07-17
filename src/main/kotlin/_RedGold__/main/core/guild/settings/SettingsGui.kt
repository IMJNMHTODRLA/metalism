package _RedGold__.main.core.guild.settings

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

object SettingsGui {
    fun openGui(player: Player) {
        val gui = SettingsHolder().inventory
        gui.item(BACKGROUND)

        gui.item[12] = getItem(Material.DIAMOND_SWORD, "&c&l리더 설정 메뉴")
        gui.item[14] = getItem(Material.IRON_INGOT, "&e&l맴버 설정 메뉴")

        player.inv + gui
        player.sendSound(Sound.UI_BUTTON_CLICK)
    }
}