package _RedGold__.main.command.style.sys

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class StyleHolder(
    val isBuy: MutableList<Boolean>,
    val isApply: Int
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 6 * 9, "칭호 선택")
    }
}