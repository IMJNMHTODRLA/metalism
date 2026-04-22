package _RedGold__.main.commands.user.shop.listeners.userShop.userItemListGui

import org.bukkit.Bukkit
import org.bukkit.inventory.InventoryHolder

class UserItemListHolder(
    val page: Int
) : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 6 * 9, "유저 상점($page)")
    override fun getInventory() = inventory
}