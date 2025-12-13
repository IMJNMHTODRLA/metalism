package _RedGold__.main.command.betting.sys.highLow

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class HighLowHolder(
    var isStart: Boolean = false,
    var select: Byte = -1,
    var betGold: Long = 0
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 6 * 9, "HIGHLOW 도박(성공: x1.5[50은 20배], 실패: x0)")
    }
}