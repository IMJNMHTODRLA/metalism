package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.confirmGui

import _RedGold__.main.functions.EasyHolder
import _RedGold__.main.managers.userShopManager.UserShopGoodsEnum
import org.bukkit.inventory.ItemStack

class ConfirmHolder(
    val returnPage: Int,
    var itemData: ItemStack,

    var type: UserShopGoodsEnum,
    var price: Long
) : EasyHolder(3 * 9) {
    override fun title() = "유저 상점(RETURN_PROFILE[$returnPage]|CONFIRM)/ESC를 눌러 돌아가기"
}