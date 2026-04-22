package _RedGold__.main.commands.user.betting.listeners.lottoGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class LottoHolder(
    var isStart: Boolean = false,
    var select: MutableList<Int> = mutableListOf(1, 1, 1, 1, 1, 1)
) : InventoryHolder {
    private val inventory = Bukkit.createInventory(this, 6 * 9, "로또 추첨")
    override fun getInventory(): Inventory = inventory
}