package _RedGold__.main.commands.user.shop.listeners._cashShop고쳐야함_.joinGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class JoinHolder : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 4 * 9, "접속 메시지 상점")
    }
}