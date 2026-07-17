package _RedGold__.main.commands.user.shop.listeners.dailyShop.crystalDailyGui

import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class CrystalDailyListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is CrystalDailyHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val clickType = event.click
        val slot = event.slot

        if (
            (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) &&
            slot in 10..16
        ) CrystalDailyConst.buy(player, slot - 10)
    }
}