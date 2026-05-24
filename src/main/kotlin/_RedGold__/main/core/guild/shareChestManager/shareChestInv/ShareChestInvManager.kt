package _RedGold__.main.core.guild.shareChestManager.shareChestInv

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryInteractEvent

fun updateShareChestInv(event: InventoryInteractEvent) {
    val gui = event.view.topInventory
    if (gui.holder !is ShareChestInvHolder) return

    gui.viewers.filterIsInstance<Player>()
        .forEach { it.updateInventory() }
}