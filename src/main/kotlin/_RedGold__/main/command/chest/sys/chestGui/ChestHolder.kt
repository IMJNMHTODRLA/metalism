package _RedGold__.main.command.chest.sys.chestGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime

class ChestHolder(
    var isOpenChest: Boolean
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 3 * 9, "창고")
    }
}