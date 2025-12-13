package _RedGold__.main.command.ranking.sys.selectGui

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
                10 -> player.performCommand("ranking gold")
                12 -> player.performCommand("ranking kill")
                14 -> player.performCommand("ranking death")
                16 -> player.performCommand("ranking boost")
            }
        }
    }
}