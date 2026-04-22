package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.infoItemGui

import _RedGold__.main.functions.EasyHolder
import _RedGold__.main.managers.userShopManager.UserShopDetailData

class InfoItemHolder(
    val returnPage: Int,
    var detail: UserShopDetailData? = null
) : EasyHolder(3 * 9) {
    override fun title() = "유저 상점(RETURN_REGGED:LIST[$returnPage]|INFO)/ESC를 눌러 돌아가기"
}