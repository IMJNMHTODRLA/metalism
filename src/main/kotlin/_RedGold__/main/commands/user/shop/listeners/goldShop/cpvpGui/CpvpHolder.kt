package _RedGold__.main.commands.user.shop.listeners.goldShop.cpvpGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class CpvpHolder : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 4 * 9, "CPVP 상점")
    override fun getInventory() = inventory
}