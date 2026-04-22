package _RedGold__.main.commands.user.shop.listeners.goldShop.foodGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class FoodHolder : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 4 * 9, "음식 상점")
    override fun getInventory() = inventory
}