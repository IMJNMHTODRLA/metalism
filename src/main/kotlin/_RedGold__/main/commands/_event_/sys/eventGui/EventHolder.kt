package _RedGold__.main.commands._event_.sys.eventGui

import _RedGold__.main.Main.Event.EVENT_NAME
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class EventHolder : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 5 * 9, "현재 진행되는 이벤트($EVENT_NAME)")
    }
}