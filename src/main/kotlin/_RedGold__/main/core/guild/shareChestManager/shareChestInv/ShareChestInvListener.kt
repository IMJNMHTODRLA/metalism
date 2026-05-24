package _RedGold__.main.core.guild.shareChestManager.shareChestInv

import _RedGold__.main.loads.RequireListener
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent

@RequireListener
class ShareChestInvListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) = updateShareChestInv(event)

    @EventHandler
    fun onInventoryDrag(event: InventoryDragEvent) = updateShareChestInv(event)
}