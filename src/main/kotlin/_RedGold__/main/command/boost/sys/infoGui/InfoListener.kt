package _RedGold__.main.command.boost.sys.infoGui

import _RedGold__.main.load.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class InfoListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is InfoHolder) {
            val player = event.whoClicked as Player
            val slot = event.slot
            event.isCancelled = true

            when (slot) {
                22 -> {
                    player.performCommand("boost discord")
                    player.closeInventory()
                }
            }
        }
    }
}