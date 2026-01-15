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
                3 -> player.performCommand("ranking gold")
                4 -> player.performCommand("ranking cash")
                5 -> player.performCommand("ranking playtime")

                13 -> player.performCommand("ranking boost")

                21 -> player.performCommand("ranking kill")
                22 -> player.performCommand("ranking death")
                23 -> player.performCommand("ranking streak")
            }
        }
    }
}