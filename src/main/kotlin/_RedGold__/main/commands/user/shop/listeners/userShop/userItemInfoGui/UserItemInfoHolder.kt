package _RedGold__.main.commands.user.shop.listeners.userShop.userItemInfoGui

import _RedGold__.main.managers.userShopManager.UserShopDetailData
import org.bukkit.Bukkit
import org.bukkit.inventory.InventoryHolder

class UserItemInfoHolder(
    val returnPage: Int,
    var isBuy: Boolean = false,
    var itemData: UserShopDetailData? = null
) : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 3 * 9, "유저 상점(RETURN_$returnPage|INFO)/ESC를 눌러 돌아가기")
    override fun getInventory() = inventory
}