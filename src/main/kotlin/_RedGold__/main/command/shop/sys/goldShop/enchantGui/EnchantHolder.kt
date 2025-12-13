package _RedGold__.main.command.shop.sys.goldShop.enchantGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class EnchantHolder(
    val page: Int
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 4 * 9, "인첸트 북 상점($page)")
    }
}