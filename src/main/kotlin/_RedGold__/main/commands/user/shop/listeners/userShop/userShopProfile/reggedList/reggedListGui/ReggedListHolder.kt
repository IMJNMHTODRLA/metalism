package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.reggedListGui

import _RedGold__.main.functions.EasyHolder
import _RedGold__.main.managers.userShopManager.UserShopEntry
import _RedGold__.main.managers.userShopManager.UserShopInfoData

class ReggedListHolder(
    val returnPage: Int,
    val newReggedItems: MutableList<UserShopEntry>,
    val preReggedItems: List<UserShopInfoData>,
) : EasyHolder(6) {
    override fun title() = "유저 상점(RETURN_PROFILE[$returnPage]|LIST)/ESC를 눌러 돌아가기"
}