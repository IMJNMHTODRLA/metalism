package _RedGold__.main.commands.user.boost.listeners.selectGui

import _RedGold__.main.commands.user.boost.Boost
import _RedGold__.main.functions.onCommand
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
            11 -> player.onCommand<Boost>("info")
            13 -> player.onCommand<Boost>("apply")
            15 -> player.onCommand<Boost>("setting")
        }
    }
}