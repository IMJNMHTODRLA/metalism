package _RedGold__.main.commands.boost.sys.infoGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class InfoHolder : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 5 * 9, "후원 정보")
    }
}