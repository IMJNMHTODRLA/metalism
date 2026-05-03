package _RedGold__.main.commands.user.mission.listeners.dailyGui

import _RedGold__.main.functions.EasyHolder
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class DailyHolder : EasyHolder(6 * 9) {
    override fun title() = "일일 미션"
}