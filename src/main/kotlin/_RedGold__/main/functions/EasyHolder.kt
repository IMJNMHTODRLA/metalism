package _RedGold__.main.functions

import org.bukkit.Bukkit
import org.bukkit.inventory.InventoryHolder

abstract class EasyHolder(private val size: Int) : InventoryHolder {
    var isClose: Boolean = false

    abstract fun title(): String
    private val insideInventory by lazy {
        Bukkit.createInventory(this, size, title())
    }

    override fun getInventory() = insideInventory
}
