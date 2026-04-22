package _RedGold__.main.commands.user.ranking.listeners.deathStreakGui

import org.bukkit.Bukkit
import org.bukkit.inventory.InventoryHolder

class DeathStreakHolder(
    var page: Int
) : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 6 * 9, "데스 순위($page)")
    override fun getInventory() = inventory
}