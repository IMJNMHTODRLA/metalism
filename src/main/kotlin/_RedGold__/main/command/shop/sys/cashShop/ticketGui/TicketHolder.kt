package _RedGold__.main.command.shop.sys.cashShop.ticketGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class TicketHolder(
    var isRoulette: Boolean = false,
    var resultValue: MutableList<String> = mutableListOf(),
    var isOpen: MutableList<Boolean> = mutableListOf(false, false, false, false, false),
    var isChange: MutableList<String> = mutableListOf("", "", "", "", "")
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 3 * 9, "뽑기 상점")
    }
}