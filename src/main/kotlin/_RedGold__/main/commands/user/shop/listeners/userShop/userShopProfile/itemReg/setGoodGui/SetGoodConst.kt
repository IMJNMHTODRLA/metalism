package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.setGoodGui

import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.managers.userShopManager.UserShopGoodsEnum

object SetGoodConst {
    val DefaultGood = { type: UserShopGoodsEnum ->
        when(type) {
            UserShopGoodsEnum.GOLD -> 10000L
            UserShopGoodsEnum.CRYSTAL -> 10L
        }
    }

    val MaxGood = { type: UserShopGoodsEnum ->
        when(type) {
            UserShopGoodsEnum.GOLD -> 9_999_999_999_990_000L //9999조 9999억 9999만
            UserShopGoodsEnum.CRYSTAL -> 2_000_000_000L //20억
        }
    }

    fun removeName(good: Long, goodType: UserShopGoodsEnum) = "&c-${good.toFormat()} ${goodType.displayName}"
    fun addName(good: Long, goodType: UserShopGoodsEnum) = "&a+${good.toFormat()} ${goodType.displayName}"

    fun removeMessage(newGood: Long, good: Long, goodType: UserShopGoodsEnum): String {
        return "&c가격에서 ${(-good).toFormat()} ${goodType.displayName} 차감하였습니다. 가격: ${newGood.toFormat()}"
    }
    fun addMessage(newGood: Long, good: Long, goodType: UserShopGoodsEnum): String {
        return "&c가격에서 ${good.toFormat()} ${goodType.displayName} 추가하였습니다. 가격: ${newGood.toFormat()}"
    }

    fun currentName(good: Long, goodType: UserShopGoodsEnum) = "&6&l설정한 가격&f:${goodType.color} $good ${goodType.displayName}"

    fun setLore(good: Long, type: String) = listOf("", "&7클릭 시 가격을 ${good.toFormat()} 골드만큼 ${type}합니다.")
}