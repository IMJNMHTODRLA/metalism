package _RedGold__.main.command.shop.sys.goldShop.mineralGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class MineralHolder(
    val sell: List<Int>,
    val pur: List<Int>
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 4 * 9, "광물 상점")
    }
}