package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.infoItemGui

import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.reggedListGui.ReggedListGui
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class InfoItemListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player as? Player?: return
        val holder = event.inventory.holder as? InfoItemHolder?: return

        plugin.task(1) {
            if (!player.isOnline) return@task
            ReggedListGui().openGui(player, holder.returnPage)
        }
    }
}