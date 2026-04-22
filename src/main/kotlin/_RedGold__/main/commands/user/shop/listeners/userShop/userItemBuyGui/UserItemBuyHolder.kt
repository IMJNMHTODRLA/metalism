package _RedGold__.main.commands.user.shop.listeners.userShop.userItemBuyGui

import _RedGold__.main.functions.EasyHolder
import _RedGold__.main.managers.userShopManager.UserShopDetailData

class UserItemBuyHolder(
    val returnPage: Int,
    var itemData: UserShopDetailData,
    var isBuy: Boolean = false,
) : EasyHolder(3 * 9) {
    override fun title() = "유저 상점(RETURN_$returnPage|BUY)/ESC를 눌러 돌아가기"
}