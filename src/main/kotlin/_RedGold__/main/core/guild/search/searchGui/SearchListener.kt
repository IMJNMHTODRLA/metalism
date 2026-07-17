package _RedGold__.main.core.guild.search.searchGui

import _RedGold__.main.core.guild.join.joinGuildWithId
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class SearchListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        val holder = gui.holder as? SearchHolder?: return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player

        when(
            val slot = event.slot
        ) {
            45 -> SearchGui.openGui(player, holder.keyword, (holder.page - 1).coerceAtLeast(0))
            53 -> SearchGui.openGui(player, holder.keyword, holder.page + 1)

            else -> {
                val id = getIdFromSlot(holder.page, slot)
                val info = holder.infoList.getOrNull(id)?: return

                player.closeInventory()

                joinGuildWithId(info.id, player)
            }
        }
    }
}