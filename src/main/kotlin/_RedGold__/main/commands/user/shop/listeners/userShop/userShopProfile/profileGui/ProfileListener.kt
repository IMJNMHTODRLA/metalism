package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.profileGui

import _RedGold__.main.commands.user.shop.listeners.userShop.userItemListGui.UserItemListGui
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.uploadItemGui.UploadItemGui
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.reggedListGui.ReggedListGui
import _RedGold__.main.functions.task
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

@RequireListener
class ProfileListener : Listener {
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player as? Player?: return
        val holder = event.inventory.holder as? ProfileHolder?: return

        task(1) {
            if (!player.isOnline) return@task
            UserItemListGui().openGui(player, holder.returnPage)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is ProfileHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val holder = gui.holder as ProfileHolder
        val slot = event.slot

        when(slot) {
            3 -> UploadItemGui().openGui(player, holder.returnPage)
            5 -> ReggedListGui().openGui(player, holder.returnPage)
        }
    }
}