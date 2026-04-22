package _RedGold__.main.commands.user.betting.listeners.diceGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class DiceHolder(
    var isStart: Boolean = false,
    var select: Int? = null,
    var betGold: Long = 0
) : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 6 * 9, "주사위 굴리기 도박(성공: x4.5, 실패: x0)")
    override fun getInventory(): Inventory = inventory
}