package _RedGold__.main.command.betting.sys.lottoGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class LottoHolder(
    var isStart: Boolean = false,
    var select: MutableList<Int> = mutableListOf(1, 1, 1, 1, 1, 1)
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 6 * 9, "로또 추첨")
    }
}