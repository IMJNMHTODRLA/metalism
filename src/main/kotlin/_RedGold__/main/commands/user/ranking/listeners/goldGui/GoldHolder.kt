package _RedGold__.main.commands.user.ranking.listeners.goldGui

import org.bukkit.Bukkit
import org.bukkit.inventory.InventoryHolder

class GoldHolder(
    var page: Int
) : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 6 * 9, "골드 순위($page)")
    override fun getInventory() = inventory
}