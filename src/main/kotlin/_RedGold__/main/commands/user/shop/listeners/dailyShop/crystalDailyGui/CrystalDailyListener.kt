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

        if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
            when (slot) {
                11 -> CrystalDailyConst.buy(player, 0)
                12 -> CrystalDailyConst.buy(player, 1)
                13 -> CrystalDailyConst.buy(player, 2)
                14 -> CrystalDailyConst.buy(player, 3)
                15 -> CrystalDailyConst.buy(player, 4)
            }
        }
    }
}