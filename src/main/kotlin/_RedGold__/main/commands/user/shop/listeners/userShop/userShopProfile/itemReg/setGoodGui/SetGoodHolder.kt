package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.setGoodGui

import _RedGold__.main.functions.EasyHolder
import _RedGold__.main.managers.userShopManager.UserShopGoodsEnum
import jdk.jfr.DataAmount
import org.bukkit.inventory.ItemStack

class SetGoodHolder(
    val returnPage: Int,
    var itemData: ItemStack,

    var type: UserShopGoodsEnum,
    var price: Long
) : EasyHolder(4 * 9) {
    override fun title() = "유저 상점(RETURN_PROFILE[$returnPage]|SET_GOOD)/ESC를 눌러 돌아가기"
}