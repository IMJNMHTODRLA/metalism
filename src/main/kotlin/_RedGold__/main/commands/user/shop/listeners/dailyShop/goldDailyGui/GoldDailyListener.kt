package _RedGold__.main.commands.user.shop.listeners.dailyShop.goldDailyGui

import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class GoldDailyListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is GoldDailyHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val clickType = event.click
        val slot = event.slot

        if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
            when (slot) {
                10 -> GoldDailyConst.buy(player, 0)
                11 -> GoldDailyConst.buy(player, 1)
                12 -> GoldDailyConst.buy(player, 2)
                13 -> GoldDailyConst.buy(player, 3)
                14 -> GoldDailyConst.buy(player, 4)
                15 -> GoldDailyConst.buy(player, 5)
                16 -> GoldDailyConst.buy(player, 6)
            }
        }
    }
}