package _RedGold__.main.commands.user.betting.listeners.selectGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class SelectHolder : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 3 * 9, "도박 선택")
    override fun getInventory(): Inventory = inventory
}
