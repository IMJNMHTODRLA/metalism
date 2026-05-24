package _RedGold__.main.commands.user.shop.listeners.userShop.userItemInfoGui

import _RedGold__.main.commands.user.shop.listeners.userShop.userItemBuyGui.UserItemBuyGui
import _RedGold__.main.commands.user.shop.listeners.userShop.userItemListGui.UserItemListGui
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireJavaPlugin
@RequireListener
class UserItemInfoListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player as? Player?: return
        val holder = event.inventory.holder as? UserItemInfoHolder?: return
        if (holder.isBuy) return //true는 넘겨, false는 붙잡기

        plugin.task(1) {
            if (!player.isOnline) return@task
            if (holder.isBuy) return@task

            UserItemListGui().openGui(player, holder.returnPage)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is UserItemInfoHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val holder = gui.holder as UserItemInfoHolder
        val clickType = event.click
        val slot = event.slot

        if (clickType == ClickType.LEFT && slot == 14) {
            holder.isBuy = true
            UserItemBuyGui().openGui(player, holder.returnPage, holder.itemData?: return)
        }
    }
}