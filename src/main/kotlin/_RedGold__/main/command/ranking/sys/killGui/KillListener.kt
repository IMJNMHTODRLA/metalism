package _RedGold__.main.command.ranking.sys.killGui

import _RedGold__.main.load.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class KillListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is KillHolder) {
            val player = event.whoClicked as Player
            val slot = event.slot
            val gui = event.inventory
            val holder = gui.holder as KillHolder
            event.isCancelled = true

            when (slot) {
                45 -> if (holder.page != 0) KillGui().openGui(player, holder.page - 1)
                53 -> KillGui().openGui(player, holder.page + 1)
            }
        }
    }
}