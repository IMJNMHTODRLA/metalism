package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.uploadItemGui

import _RedGold__.main.functions.EasyHolder
import org.bukkit.inventory.ItemStack

class UploadItemHolder(
    val returnPage: Int,
    var itemData: ItemStack? = null,
) : EasyHolder(3 * 9) {
    override fun title() = "유저 상점(RETURN_PROFILE[$returnPage]|UPLOAD)/ESC를 눌러 돌아가기"
}