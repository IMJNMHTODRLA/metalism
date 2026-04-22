package _RedGold__.main.commands.user.ranking.listeners.boostGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class BoostHolder(
    var page: Int
) : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 6 * 9, "후원 순위($page)")
    override fun getInventory() = inventory
}