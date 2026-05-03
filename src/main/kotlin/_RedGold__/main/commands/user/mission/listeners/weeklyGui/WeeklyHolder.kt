package _RedGold__.main.commands.user.mission.listeners.weeklyGui

import _RedGold__.main.functions.EasyHolder
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class WeeklyHolder : EasyHolder(6 * 9) {
    override fun title() = "주간 미션"
}