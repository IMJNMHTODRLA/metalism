package _RedGold__.main.commands.user.shop.listeners.monthlyShop

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Sound
import org.bukkit.entity.Player

class MonthlyGui {
    fun openGui(player: Player) {
        val gui = MonthlyHolder().inventory
        gui.item(BACKGROUND)

        MonthlyConst.setShopItem()

        player.inv + gui
        player.sendSound(Sound.UI_BUTTON_CLICK)
    }
}