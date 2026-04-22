package _RedGold__.main.commands.user.shop.listeners.cashShop고쳐야함.toolGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class ToolHolder(
    var isPreview: Boolean = false
) : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 4 * 9, "도구 상점(But 포인트를 곁들인)")
    }
}