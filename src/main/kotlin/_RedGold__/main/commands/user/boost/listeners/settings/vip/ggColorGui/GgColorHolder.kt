package _RedGold__.main.commands.user.boost.listeners.settings.vip.ggColorGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class GgColorHolder(
    val page: Int,
    val isPlusRank: Boolean
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 4 * 9, "GG 색깔 바꾸기")
    }
}