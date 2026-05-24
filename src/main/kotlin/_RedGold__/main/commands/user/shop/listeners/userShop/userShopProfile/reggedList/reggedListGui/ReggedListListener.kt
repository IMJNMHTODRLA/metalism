package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.reggedListGui

import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.ProfileGlobalConst
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.uploadItemGui.UploadItemGui
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.profileGui.ProfileGui
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.collectItemGui.CollectItemGui
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.infoItemGui.InfoItemGui
import _RedGold__.main.functions.task
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

class ReggedListListener : Listener {
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player as? Player?: return
        val holder = event.inventory.holder as? ReggedListHolder?: return

        task(1) {
            if (!player.isOnline) return@task
            if (!holder.isClose) return@task
            ProfileGui().openGui(player, holder.returnPage)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is ReggedListHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val holder = gui.holder as ReggedListHolder

        val clickType = event.click
        val slot = event.slot

        val playerMaxReg = ProfileGlobalConst.maxRegList(PermissionEnum[player])
        val allItems = holder.newReggedItems + holder.preReggedItems

        if (slot >= playerMaxReg) return

        val item = allItems.getOrNull(slot)?: run {
            holder.isClose = true
            UploadItemGui().openGui(player, holder.returnPage)
            return
        }

        when(clickType) {
            ClickType.LEFT -> {
                holder.isClose = true
                CollectItemGui().openGui(player, holder.returnPage, item)
            }
            ClickType.RIGHT -> {
                holder.isClose = true
                InfoItemGui().openGui(player, holder.returnPage, item)
            }

            else -> {}
        }
    }
}