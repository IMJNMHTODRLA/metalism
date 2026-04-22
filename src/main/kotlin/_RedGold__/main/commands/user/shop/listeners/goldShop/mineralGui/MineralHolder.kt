package _RedGold__.main.commands.user.shop.listeners.goldShop.mineralGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class MineralHolder : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 4 * 9, "광물 상점")
    override fun getInventory() = inventory
}