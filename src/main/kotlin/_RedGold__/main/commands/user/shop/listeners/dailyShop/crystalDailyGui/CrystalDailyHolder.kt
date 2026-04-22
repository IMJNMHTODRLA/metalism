package _RedGold__.main.commands.user.shop.listeners.dailyShop.crystalDailyGui

import org.bukkit.Bukkit
import org.bukkit.inventory.InventoryHolder

class CrystalDailyHolder : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 4 * 9, "일일 상점(크리스탈)")
    override fun getInventory() = inventory
}