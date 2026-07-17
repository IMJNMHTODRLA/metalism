package _RedGold__.main.commands._event_.sys.rewardGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class RewardHolder(
    val getList: MutableList<Boolean>,
    val page: Int
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 6 * 9, "이벤트 보상($page)")
    }
}