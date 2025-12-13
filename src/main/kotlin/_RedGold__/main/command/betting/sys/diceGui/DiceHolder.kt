package _RedGold__.main.command.betting.sys.diceGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class DiceHolder(
    var isStart: Boolean = false,
    var select: Byte = -1,
    var betGold: Long = 0
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 6 * 9, "주사위 굴리기 도박(성공: x4.5, 실패: x0)")
    }
}