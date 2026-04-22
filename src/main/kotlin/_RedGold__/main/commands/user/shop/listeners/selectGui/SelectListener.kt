package _RedGold__.main.commands.user.shop.listeners.selectGui

import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class SelectListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is SelectHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val slot = event.slot

        when (slot) {
            11 -> player.performCommand("shop gold")
            12 -> player.performCommand("shop crystal")
            13 -> player.performCommand("shop user")
            14 -> player.performCommand("shop daily")
            15 -> player.performCommand("shop monthly")
        }
    }
}