package _RedGold__.main.commands.mission.sys.dailyGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class DailyHolder(
    val progressList: MutableList<Int>,
    val getList: MutableList<Boolean>
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 6 * 9, "일일 미션")
    }
}