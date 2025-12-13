package _RedGold__.main.command.home.sys.home

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class HomeHolder(
    var buyHome: MutableList<Int>,
    var saveHome: MutableList<String>,
    var homePrice: MutableList<Int>
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 1 * 9, "홈 관리")
    }
}