package _RedGold__.main.commands.user.mission.listeners.dailyGui

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