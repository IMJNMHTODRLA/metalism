package _RedGold__.main.event._showDown_.selectGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class SelectHolder : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 5 * 9, "난이도 선택(ESC를 눌러 취소)")
    }
}