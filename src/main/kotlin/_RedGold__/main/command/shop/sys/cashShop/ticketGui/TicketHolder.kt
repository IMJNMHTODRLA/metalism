package _RedGold__.main.command.shop.sys.cashShop.ticketGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class TicketHolder(
    var isRoulette: Boolean = false
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 3 * 9, "뽑기 상점")
    }
}