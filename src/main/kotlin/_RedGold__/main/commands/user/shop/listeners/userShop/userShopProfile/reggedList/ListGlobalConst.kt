package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList

import _RedGold__.main.managers.userShopManager.UserShopDetailData
import _RedGold__.main.managers.userShopManager.UserShopEntry
import _RedGold__.main.managers.userShopManager.UserShopInfoData
import _RedGold__.main.managers.userShopManager.getItemFromId

object ListGlobalConst {
    fun getDBDetail(data: Any): UserShopDetailData? {
        return when (data) {
            is UserShopEntry -> data.detail
            is UserShopInfoData -> getItemFromId(data.id)
            else -> null
        }
    }
}