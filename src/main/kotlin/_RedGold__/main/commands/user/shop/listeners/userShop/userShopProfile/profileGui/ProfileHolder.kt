package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.profileGui

import _RedGold__.main.functions.EasyHolder

class ProfileHolder(
    val returnPage: Int
) : EasyHolder(1 * 9) {
    override fun title() = "유저 상점(RETURN_$returnPage|PROFILE)/ESC를 눌러 돌아가기"
}