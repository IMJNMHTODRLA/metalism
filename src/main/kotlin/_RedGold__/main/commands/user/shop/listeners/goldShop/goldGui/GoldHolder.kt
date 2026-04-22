package _RedGold__.main.commands.user.shop.listeners.goldShop.goldGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class GoldHolder : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 3 * 9, "골드 상점 선택")
    override fun getInventory() = inventory
}