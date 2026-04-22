package _RedGold__.main.commands.user.ranking.listeners.playTimeGui

import _RedGold__.main.commands.user.ranking.listeners.refreshManager.RefreshManager.RankValue.waitUpdate
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class PlayTimeHolder(
    var page: Int
) : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 6 * 9, "골드 순위($page)")
    override fun getInventory() = inventory
}