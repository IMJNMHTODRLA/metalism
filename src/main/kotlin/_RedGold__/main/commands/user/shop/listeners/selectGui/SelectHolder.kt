package _RedGold__.main.commands.user.shop.listeners.selectGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class SelectHolder : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 3 * 9, "상점 선택")
    override fun getInventory() = inventory
}