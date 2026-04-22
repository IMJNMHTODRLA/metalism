package _RedGold__.main.commands.user.shop.listeners.goldShop.plantGui

import org.bukkit.Bukkit
import org.bukkit.inventory.InventoryHolder

class PlantHolder : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 4 * 9, "농작물 상점")
    override fun getInventory() = inventory
}