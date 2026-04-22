package _RedGold__.main.commands.user.shop.listeners.goldShop.enchantGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class EnchantHolder(
    val page: Int
) : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 4 * 9, "인첸트 북 상점($page)")
    override fun getInventory() = inventory
}