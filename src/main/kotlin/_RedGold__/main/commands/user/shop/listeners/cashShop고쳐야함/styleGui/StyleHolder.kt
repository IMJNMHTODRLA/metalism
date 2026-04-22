package _RedGold__.main.commands.user.shop.listeners.cashShop고쳐야함.styleGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class StyleHolder(
    val isBuy: MutableList<Boolean>
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 4 * 9, "칭호 상점")
    }
}