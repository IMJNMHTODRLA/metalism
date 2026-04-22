package _RedGold__.main.commands.user.shop.listeners.dailyShop.dailyGui

import _RedGold__.main.commands.user.shop.listeners.dailyShop.crystalDailyGui.CrystalDailyGui
import _RedGold__.main.commands.user.shop.listeners.dailyShop.goldDailyGui.GoldDailyGui
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class DailyListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is DailyHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val slot = event.slot

        when (slot) {
            12 -> GoldDailyGui().openGui(player)

            14 -> CrystalDailyGui().openGui(player)
        }
    }
}