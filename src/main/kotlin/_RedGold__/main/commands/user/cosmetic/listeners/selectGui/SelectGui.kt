package _RedGold__.main.commands.user.cosmetic.listeners.selectGui

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

        gui.item[11] = getItem(Material.NAME_TAG, "&e칭호 관리")
        gui.item[12] = getItem(Material.NAME_TAG, "&e접속 메시지 관리")
        gui.item[13] = getItem(Material.REDSTONE, "&e사망 사운드 관리")
        gui.item[14] = getItem(Material.NETHERITE_SWORD, "&e킬 사운드 관리")

        player.inv + gui
        player.sendSound(Sound.UI_BUTTON_CLICK)
    }
}