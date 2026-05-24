package _RedGold__.main.core.guild.settings.leader.whitelist

import _RedGold__.main.core.guild.settings.leader.whitelist.whitelistRemove.WhitelistRemoveGui
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class WhitelistListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        val holder = gui.holder as? WhitelistHolder?: return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        when(
            val slot = event.slot
        ) {
            45 -> WhitelistGui.openGui(player, (holder.page - 1).coerceAtLeast(0))
            53 -> WhitelistGui.openGui(player, holder.page + 1)

            else -> {
                val id = getId(slot)?: return
                val target = holder.data.getOrNull(id)?: return

                WhitelistRemoveGui.openGui(player, target)
            }
        }
    }
}