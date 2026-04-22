package _RedGold__.main.commands.user.shop.listeners.dailyShop.dailyGui

import org.bukkit.Bukkit
import org.bukkit.inventory.InventoryHolder

class DailyHolder : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 3 * 9, "일일 상점 선택")
    override fun getInventory() = inventory
}