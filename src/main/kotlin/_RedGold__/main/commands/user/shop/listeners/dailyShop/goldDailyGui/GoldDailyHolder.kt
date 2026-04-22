package _RedGold__.main.commands.user.shop.listeners.dailyShop.goldDailyGui

import org.bukkit.Bukkit
import org.bukkit.inventory.InventoryHolder

class GoldDailyHolder : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 4 * 9, "일일 상점(골드)")
    override fun getInventory() = inventory
}