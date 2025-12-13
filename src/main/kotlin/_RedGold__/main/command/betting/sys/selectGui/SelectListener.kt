package _RedGold__.main.command.betting.sys.selectGui

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
                10 -> player.performCommand("betting coin")
                12 -> player.performCommand("betting dice")
                14 -> player.performCommand("betting highlow")
                16 -> player.performCommand("betting lotto")
            }
        }
    }
}