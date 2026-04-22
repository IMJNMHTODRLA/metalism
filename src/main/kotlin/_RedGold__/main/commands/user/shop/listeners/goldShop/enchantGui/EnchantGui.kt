package _RedGold__.main.commands.user.shop.listeners.goldShop.enchantGui

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

class EnchantGui {
    fun openGui(player: Player, page: Int) {
        val gui = EnchantHolder(page).inventory

        gui.item[0..44] = BACKGROUND
        gui.item[45..gui.end] = BACKGROUND_1

        val setShopItem = EnchantConst::setShopItem
        val pageFormulas = EnchantConst.pageFormulas

        gui.item[10] = setShopItem(pageFormulas(page, 0))
        gui.item[11] = setShopItem(pageFormulas(page, 1))
        gui.item[12] = setShopItem(pageFormulas(page, 2))
        gui.item[13] = setShopItem(pageFormulas(page, 3))
        gui.item[14] = setShopItem(pageFormulas(page, 4))
        gui.item[15] = setShopItem(pageFormulas(page, 5))
        gui.item[16] = setShopItem(pageFormulas(page, 6))

        gui.item[19] = setShopItem(pageFormulas(page, 7))
        gui.item[20] = setShopItem(pageFormulas(page, 8))
        gui.item[21] = setShopItem(pageFormulas(page, 9))
        gui.item[22] = setShopItem(pageFormulas(page, 10))
        gui.item[23] = setShopItem(pageFormulas(page, 11))
        gui.item[24] = setShopItem(pageFormulas(page, 12))
        gui.item[25] = setShopItem(pageFormulas(page, 13))

        gui.item[27] = getItem(
            Material.RED_STAINED_GLASS_PANE,
            "&c&l이전 페이지로 이동"
        )

        gui.item[31] = getItem(
            Material.BOOK,
            "&8&l현재 페이지: ($page/${EnchantConst.MAX_PAGE})"
        )

        gui.item[35] = getItem(
            Material.GREEN_STAINED_GLASS_PANE,
            "&a&l다음 페이지로 이동"
        )

        player.inv + gui
        player.sendSound(Sound.UI_BUTTON_CLICK)
    }
}