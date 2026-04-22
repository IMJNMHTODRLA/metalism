package _RedGold__.main.commands.user.shop.listeners.cashShop고쳐야함.deathGui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class DeathHolder : InventoryHolder {
    override fun getInventory(): Inventory {
        return Bukkit.createInventory(this, 4 * 9, "사망 사운드 상점(But 포인트를 곁들인)")
    }
}