package _RedGold__.main.command.betting.sys.coinGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class CoinHolder(
    var isStart: Boolean = false,
    var sniffling: Boolean = false,
    var betGold: Long = 0
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 6 * 9, "동전 던지기 도박(성공: x1.5, 실패: x0)")
    }
}