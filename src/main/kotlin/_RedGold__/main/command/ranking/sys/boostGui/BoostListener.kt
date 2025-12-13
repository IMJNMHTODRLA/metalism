package _RedGold__.main.command.ranking.sys.boostGui

import _RedGold__.main.load.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class BoostListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is BoostHolder) {
            val player = event.whoClicked as Player
            val slot = event.slot
            val gui = event.inventory
            val holder = gui.holder as BoostHolder
            event.isCancelled = true

            when (slot) {
                45 -> if (holder.page != 0) BoostGui().openGui(player, holder.page - 1)
                53 -> BoostGui().openGui(player, holder.page + 1)
            }
        }
    }
}