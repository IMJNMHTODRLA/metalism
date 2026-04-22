package _RedGold__.main.commands.user.shop.listeners.cashShop고쳐야함.kitGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class KitHolder(
    val buyTimes: MutableList<Int>,
    var isPreviewing: Boolean = false
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 3 * 9, "키트 상점")
    }
}