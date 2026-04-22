package _RedGold__.main.commands.user.betting.listeners.coinGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class CoinHolder(
    var isStart: Boolean = false,
    var sniffling: Boolean = false,
    var betGold: Long = 0
) : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 6 * 9, "동전 던지기 도박(성공: x1.5, 실패: x0)")
    override fun getInventory(): Inventory = inventory
}