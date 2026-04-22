package _RedGold__.main.commands.user.ranking.listeners.killStreakGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class KillStreakHolder(
    var page: Int
) : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 6 * 9, "연킬 순위($page)")
    override fun getInventory() = inventory
}