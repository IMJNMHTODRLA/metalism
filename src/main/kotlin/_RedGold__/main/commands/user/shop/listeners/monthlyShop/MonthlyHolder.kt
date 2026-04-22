package _RedGold__.main.commands.user.shop.listeners.monthlyShop

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime

class MonthlyHolder : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 3 * 9, "월간 상점")
    override fun getInventory() = inventory
}