package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.infoItemGui

import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.reggedListGui.ReggedListGui
import _RedGold__.main.functions.task
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent

@RequireListener
class InfoItemListener : Listener {
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player as? Player?: return
        val holder = event.inventory.holder as? InfoItemHolder?: return

        task(1) {
            if (!player.isOnline) return@task
            ReggedListGui().openGui(player, holder.returnPage)
        }
    }
}