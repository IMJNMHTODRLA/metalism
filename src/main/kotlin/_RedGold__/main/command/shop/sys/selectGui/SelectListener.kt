package _RedGold__.main.command.shop.sys.selectGui

import _RedGold__.main.load.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class SelectListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is SelectHolder) {
            val player = event.whoClicked as Player
            val slot = event.slot
            event.isCancelled = true

            when (slot) {
                11 -> player.performCommand("shop gold")
                12 -> player.performCommand("shop user")
                13 -> player.performCommand("shop cash")
                14 -> player.performCommand("shop daily")
                15 -> player.performCommand("shop monthly")
            }
        }
    }
}