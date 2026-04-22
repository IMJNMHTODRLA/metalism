package _RedGold__.main.commands.user.ranking.listeners.deathGui

import org.bukkit.Bukkit
import org.bukkit.inventory.InventoryHolder

class DeathHolder(
    var page: Int
) : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 6 * 9, "데스 순위($page)")
    override fun getInventory() = inventory
}