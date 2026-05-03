package _RedGold__.main.commands.user.boost.listeners.settings.settingsGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class SelectHolder : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 3 * 9, "후원 기능 설정 선택")
    }
}