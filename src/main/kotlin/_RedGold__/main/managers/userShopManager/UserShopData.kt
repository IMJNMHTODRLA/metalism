package _RedGold__.main.managers.userShopManager

import _RedGold__.main.functions.TimeTool.now
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.*

data class UserShopInfoData(
    val id: Int,
    val uuid: UUID,

    //val priceType: UserShopGoodsEnum,
    //val priceAmount: Long,

    val createdAt: Long,

    val displayMaterial: Material,
    val displayName: String,
    val displayIsEnchant: Boolean,
    val displayAmount: Int,

    var isPurchase: Boolean = false
) {
    val isCollect get() = createdAt < (now - SELL_PERIOD)
    val isDelete get() = createdAt < (now - EXPIRE_PERIOD)
}

data class UserShopDetailData(
    val id: Int,
    val uuid: UUID,

    val priceType: UserShopGoodsEnum,
    val priceAmount: Long,

    val item: ItemStack
)

data class UserShopEntry(
    val info: UserShopInfoData,
    val detail: UserShopDetailData
)