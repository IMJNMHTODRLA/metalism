package _RedGold__.main.commands.user.mission.listeners.weeklyGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class WeeklyHolder(
    val progressList: MutableList<Int>,
    val getList: MutableList<Boolean>
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 6 * 9, "주간 미션")
    }
}