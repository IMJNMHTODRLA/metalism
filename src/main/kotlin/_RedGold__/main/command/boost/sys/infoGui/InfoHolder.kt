package _RedGold__.main.command.boost.sys.infoGui

import _RedGold__.main.command.ranking.sys.Refresh.RankValue.waitUpdate
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class InfoHolder : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 5 * 9, "후원 정보")
    }
}