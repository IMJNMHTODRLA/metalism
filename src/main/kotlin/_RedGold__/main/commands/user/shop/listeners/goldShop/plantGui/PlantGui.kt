package _RedGold__.main.commands.user.shop.listeners.goldShop.plantGui

import _RedGold__.main.functions.FastGui.enchantEffect
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

class PlantGui {
    fun openGui(player: Player) {
        val gui = PlantHolder().inventory

        gui.item[0..26] = BACKGROUND
        gui.item[27..gui.end] = BACKGROUND_1

        gui.item[10] = PlantConst.setShopItem(player, 0)
        gui.item[11] = PlantConst.setShopItem(player, 1)
        gui.item[12] = PlantConst.setShopItem(player, 2)
        gui.item[13] = PlantConst.setShopItem(player, 3)
        gui.item[14] = PlantConst.setShopItem(player, 4)
        gui.item[15] = PlantConst.setShopItem(player, 5)
        gui.item[16] = PlantConst.setShopItem(player, 6)

        gui.item[19] = PlantConst.setShopItem(player, 7)
        gui.item[20] = PlantConst.setShopItem(player, 8)
        gui.item[21] = PlantConst.setShopItem(player, 9)
        gui.item[22] = PlantConst.setShopItem(player, 10)
        gui.item[23] = PlantConst.setShopItem(player, 11)
        gui.item[24] = PlantConst.setShopItem(player, 12)
        gui.item[25] = PlantConst.setShopItem(player, 13)

        gui.item[31] = getItem(
            Material.BOOK,
            "&8&l현재 페이지: (1/1)",
            listOf("", "&e&l클릭 시 120 크리스탈을 소비해 판매 횟수를 초기화 합니다."),
        ).apply { enchantEffect() }

        player.inv + gui
        player.sendSound(Sound.UI_BUTTON_CLICK)
    }
}