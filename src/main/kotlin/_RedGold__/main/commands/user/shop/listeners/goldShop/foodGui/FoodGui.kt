package _RedGold__.main.commands.user.shop.listeners.goldShop.foodGui

import _RedGold__.main.functions.FastGui.end
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.BACKGROUND_1
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class FoodGui {
    fun openGui(player: Player) {
        val gui = FoodHolder().inventory

        gui.item[0..26] = BACKGROUND
        gui.item[27..gui.end] = BACKGROUND_1

        gui.item[10] = FoodConst.setShopItem(0)
        gui.item[11] = FoodConst.setShopItem(1)
        gui.item[12] = FoodConst.setShopItem(2)
        gui.item[13] = FoodConst.setShopItem(3)
        gui.item[14] = FoodConst.setShopItem(4)
        gui.item[15] = FoodConst.setShopItem(5)
        gui.item[16] = FoodConst.setShopItem(6)

        gui.item[19] = FoodConst.setShopItem(7)
        gui.item[20] = FoodConst.setShopItem(8)
        gui.item[21] = FoodConst.setShopItem(9)
        gui.item[22] = FoodConst.setShopItem(10)
        gui.item[23] = FoodConst.setShopItem(11)
        gui.item[24] = FoodConst.setShopItem(12)
        gui.item[25] = FoodConst.setShopItem(13)

        gui.item[31] = getItem(
            Material.BOOK,
            "&8&l현재 페이지: (1/1)"
        )

        player.inv + gui
        player.sendSound(Sound.UI_BUTTON_CLICK)
    }
}