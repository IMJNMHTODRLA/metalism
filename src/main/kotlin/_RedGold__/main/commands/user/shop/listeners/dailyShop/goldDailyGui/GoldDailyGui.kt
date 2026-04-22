package _RedGold__.main.commands.user.shop.listeners.dailyShop.goldDailyGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Sound
import org.bukkit.entity.Player

class GoldDailyGui {
    fun openGui(player: Player) {
        val gui = GoldDailyHolder().inventory
        gui.item(BACKGROUND)

        gui.item[10] = GoldDailyConst.setShopItem(0)
        gui.item[11] = GoldDailyConst.setShopItem(1)
        gui.item[12] = GoldDailyConst.setShopItem(2)
        gui.item[13] = GoldDailyConst.setShopItem(3)
        gui.item[14] = GoldDailyConst.setShopItem(4)
        gui.item[15] = GoldDailyConst.setShopItem(5)
        gui.item[16] = GoldDailyConst.setShopItem(6)

        player.inv + gui
        player.sendSound(Sound.UI_BUTTON_CLICK)
    }
}