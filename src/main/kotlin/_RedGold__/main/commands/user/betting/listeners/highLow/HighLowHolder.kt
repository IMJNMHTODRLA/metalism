package _RedGold__.main.commands.user.betting.listeners.highLow

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class HighLowHolder(
    var isStart: Boolean = false,
    var select: Int? = null,
    var betGold: Long = 0
) : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 6 * 9, "HIGHLOW 도박(성공: x1.5[50은 10배], 실패: x0)")
    override fun getInventory(): Inventory = inventory
}