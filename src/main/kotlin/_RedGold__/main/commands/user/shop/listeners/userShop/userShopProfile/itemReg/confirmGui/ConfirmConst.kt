package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.confirmGui

import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.managers.userShopManager.FEE_PERCENT
import _RedGold__.main.managers.userShopManager.UserShopGoodsEnum

object ConfirmConst {
    fun setLore(price: Long, type: UserShopGoodsEnum) = listOf("",
        "&f&l가격: ${type.color}${price.toFormat()} ${type.displayName}",
        "&e&l수수료 포함 가격&f&l: ${type.color}${(price * (1 + FEE_PERCENT)).toLong().toFormat()} ${type.displayName}"
    )
}