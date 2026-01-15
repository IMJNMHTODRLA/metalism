package _RedGold__.main.command.shop.sys.cashShop.cashGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class CashHolder(
    val point: Int,
    var isRoulette: Boolean = false,
    var resultValue: MutableList<String> = mutableListOf(),
    var isOpen: MutableList<Boolean> = mutableListOf(false, false, false, false, false),
    var isEquip: MutableList<Boolean> = mutableListOf(false, false, false, false, false),
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 3 * 9, "캐시 상점 선택?")
    }
}