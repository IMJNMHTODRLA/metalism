package _RedGold__.main.commands.user.shop.listeners.cashShop고쳐야함.killGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class KillHolder : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 4 * 9, "킬 사운드 상점")
    }
}