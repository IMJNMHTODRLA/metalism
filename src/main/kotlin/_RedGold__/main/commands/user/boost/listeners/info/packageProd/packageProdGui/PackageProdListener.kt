package _RedGold__.main.commands.user.boost.listeners.info.packageProd.packageProdGui

import _RedGold__.main.commands.user.boost.listeners.info.infoGui.InfoGui
import _RedGold__.main.commands.user.boost.listeners.info.packageProd.monthlyGui.MonthlyGui
import _RedGold__.main.commands.user.boost.listeners.info.packageProd.starterGui.StarterGui
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class PackageProdListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onCloseInventory(event: InventoryCloseEvent) {
        val gui = event.inventory
        val player = event.player as Player
        val holder = gui.holder as? PackageProdHolder?: return

        plugin.task {
            if (!player.isOnline) return@task
            if (!holder.isClose) return@task

            InfoGui().openGui(player)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        val holder = gui.holder as? PackageProdHolder?: return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val slot = event.slot

        when (slot) {
            12 -> {
                StarterGui().openGui(player)
                holder.isClose = true
            }

            14 -> {
                MonthlyGui().openGui(player)
                holder.isClose = true
            }
        }
    }
}