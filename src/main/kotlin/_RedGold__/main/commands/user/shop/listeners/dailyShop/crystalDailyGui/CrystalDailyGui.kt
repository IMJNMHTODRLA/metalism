package _RedGold__.main.commands.user.shop.listeners.dailyShop.crystalDailyGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Sound
import org.bukkit.entity.Player

class CrystalDailyGui {
    fun openGui(player: Player) {
        val gui = CrystalDailyHolder().inventory
        gui.item(BACKGROUND)

        gui.item[10] = CrystalDailyConst.setShopItem(0)
        gui.item[11] = CrystalDailyConst.setShopItem(1)
        gui.item[12] = CrystalDailyConst.setShopItem(2)
        gui.item[13] = CrystalDailyConst.setShopItem(3)
        gui.item[14] = CrystalDailyConst.setShopItem(4)
        gui.item[15] = CrystalDailyConst.setShopItem(5)
        gui.item[16] = CrystalDailyConst.setShopItem(6)

        player.inv + gui
        player.sendSound(Sound.UI_BUTTON_CLICK)
    }
}