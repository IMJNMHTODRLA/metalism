package _RedGold__.main.commands.user.ranking.listeners.killGui

import org.bukkit.Bukkit
import org.bukkit.inventory.InventoryHolder

class KillHolder(
    var page: Int
) : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 6 * 9, "킬 순위($page)")
    override fun getInventory() = inventory
}