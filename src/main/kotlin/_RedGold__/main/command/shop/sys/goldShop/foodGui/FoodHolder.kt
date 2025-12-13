package _RedGold__.main.command.shop.sys.goldShop.foodGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class FoodHolder(
    val page: Int
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 4 * 9, "음식 상점($page)")
    }
}