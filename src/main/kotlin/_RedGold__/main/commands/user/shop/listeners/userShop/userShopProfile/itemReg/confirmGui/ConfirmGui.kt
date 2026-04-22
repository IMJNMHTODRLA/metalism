package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.confirmGui

import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.userShopManager.UserShopGoodsEnum
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ConfirmGui {
    fun openGui(
        player: Player, returnPage: Int,
        itemData: ItemStack,
        type: UserShopGoodsEnum, price: Long
    ) {
        val gui = ConfirmHolder(returnPage, itemData, type, price).inventory
        gui.item(BACKGROUND)

        gui.item[13] = getItem(
            itemData.type,
            itemData.itemMeta.displayName,
            ConfirmConst.setLore(price, type)
        )

        gui.item[18] = getItem(Material.RED_STAINED_GLASS_PANE, "&c&l이전 단계로 돌아가기")
        gui.item[26] = getItem(Material.GREEN_STAINED_GLASS_PANE, "&a&l확인").apply { enchantEffect() }

        player.inv + gui
        player.sendSound(Sound.UI_BUTTON_CLICK)
    }
}