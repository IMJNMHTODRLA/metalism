package _RedGold__.main.command.mission.sys.achievementGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class AchievementHolder(
    val progressList: MutableList<Int>,
    val getList: MutableList<Boolean>
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 6 * 9, "업적 미션")
    }
}