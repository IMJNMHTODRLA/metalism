package _RedGold__.main.command.ranking.sys.goldGui

import _RedGold__.main.command.ranking.sys.cashGui.CashGui
import _RedGold__.main.load.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class GoldListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is GoldHolder) {
            val player = event.whoClicked as Player
            val slot = event.slot
            val gui = event.inventory
            val holder = gui.holder as GoldHolder
            event.isCancelled = true

            when (slot) {
                45 -> if (holder.page != 0) CashGui().openGui(player, holder.page - 1)
                53 -> CashGui().openGui(player, holder.page + 1)
            }
        }
    }
}