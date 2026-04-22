package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.setGoodGui

import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.FastGui.end
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.PlusMath.pow
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.BACKGROUND_1
import _RedGold__.main.managers.userShopManager.UserShopGoodsEnum
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class SetGoodGui {
    fun openGui(
        player: Player, returnPage: Int,
        itemData: ItemStack,
        type: UserShopGoodsEnum = UserShopGoodsEnum.GOLD, price: Long = 0
    ) {
        val gui = SetGoodHolder(returnPage, itemData, type, price).inventory
        gui.item(BACKGROUND)
        gui.item[27..gui.end] = BACKGROUND_1

        gui.item[12] = getItem(Material.GOLD_INGOT, "&6&골드").apply { if (type == UserShopGoodsEnum.GOLD) enchantEffect() }
        gui.item[14] = getItem(Material.DIAMOND, "&b&크리스탈").apply { if (type == UserShopGoodsEnum.CRYSTAL) enchantEffect() }

        gui.item[22] = getItem(
            Material.BLACK_STAINED_GLASS_PANE,
            SetGoodConst.currentName(price, type)
        )

        repeat(4) { i ->
            val good = SetGoodConst.DefaultGood(type) * 10.pow(i)

            gui.item[21 - i] = getItem(
                Material.RED_STAINED_GLASS_PANE,
                SetGoodConst.removeName(good, type),
                SetGoodConst.setLore(good, "차감")
            )

            gui.item[23 + i] = getItem(
                Material.GREEN_STAINED_GLASS_PANE,
                SetGoodConst.addName(good, type),
                SetGoodConst.setLore(good, "추가")
            )
        }

        gui.item[27] = getItem(Material.RED_STAINED_GLASS_PANE, "&c&l이전 단계로 돌아가기")
        gui.item[35] = getItem(Material.GREEN_STAINED_GLASS_PANE, "&a&l다음 단계로 진행하기")

        player.inv + gui
        player.sendSound(Sound.UI_BUTTON_CLICK)
    }
}