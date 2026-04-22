package _RedGold__.main.commands.user.shop.listeners.goldShop.mineralGui

import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.FastGui.end
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.BACKGROUND_1
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class MineralGui {
    fun openGui(player: Player) {
        val gui = MineralHolder().inventory

        gui.item[0..26] = BACKGROUND
        gui.item[27..gui.end] = BACKGROUND_1

        gui.item[10] = MineralConst.setShopItem(player, 0)
        gui.item[11] = MineralConst.setShopItem(player, 1)
        gui.item[12] = MineralConst.setShopItem(player, 2)
        gui.item[13] = MineralConst.setShopItem(player, 3)
        gui.item[14] = MineralConst.setShopItem(player, 4)
        gui.item[15] = MineralConst.setShopItem(player, 5)
        gui.item[16] = MineralConst.setShopItem(player, 6)

        gui.item[19] = MineralConst.setShopItem(player, 7)
        gui.item[20] = MineralConst.setShopItem(player, 8)
        gui.item[21] = MineralConst.setShopItem(player, 9)
        gui.item[22] = MineralConst.setShopItem(player, 10)
        gui.item[23] = MineralConst.setShopItem(player, 11)
        gui.item[24] = MineralConst.setShopItem(player, 12)
        gui.item[25] = MineralConst.setShopItem(player, 13)

        gui.item[31] = getItem(
            Material.BOOK,
            "&8&l현재 페이지: (1/1)"
        )

        player.inv + gui
        player.sendSound(Sound.UI_BUTTON_CLICK)
    }
}