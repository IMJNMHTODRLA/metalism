package _RedGold__.main.commands.user.ranking.listeners.playTimeGui

import org.bukkit.Bukkit
import org.bukkit.inventory.InventoryHolder

class PlayTimeHolder(
    var page: Int
) : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 6 * 9, "플레이 타임 순위($page)")
    override fun getInventory() = inventory
}