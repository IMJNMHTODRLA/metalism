package _RedGold__.main.commands.user.shop.listeners.goldShop.cpvpGui

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

class CpvpGui {
    fun openGui(player: Player) {
        val gui = CpvpHolder().inventory

        gui.item[0..26] = BACKGROUND
        gui.item[27..gui.end] = BACKGROUND_1

        gui.item[10] = CpvpConst.setShopItem(0)
        gui.item[11] = CpvpConst.setShopItem(1)
        gui.item[12] = CpvpConst.setShopItem(2)
        gui.item[13] = CpvpConst.setShopItem(3)
        gui.item[14] = CpvpConst.setShopItem(4)
        gui.item[15] = CpvpConst.setShopItem(5)
        gui.item[16] = CpvpConst.setShopItem(6)

        gui.item[19] = CpvpConst.setShopItem(7)
        gui.item[20] = CpvpConst.setShopItem(8)
        gui.item[21] = CpvpConst.setShopItem(9)
        gui.item[22] = CpvpConst.setShopItem(10)
        gui.item[23] = CpvpConst.setShopItem(11)
        gui.item[24] = CpvpConst.setShopItem(12)
        gui.item[25] = CpvpConst.setShopItem(13)

        gui.item[31] = getItem(
            Material.BOOK,
            "&8&l현재 페이지: (1/1)"
        )

        player.inv + gui
        player.sendSound(Sound.UI_BUTTON_CLICK)
    }
}