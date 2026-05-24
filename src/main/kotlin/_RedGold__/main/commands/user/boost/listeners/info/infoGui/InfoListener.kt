package _RedGold__.main.commands.user.boost.listeners.info.infoGui

import _RedGold__.main.commands.user.boost.listeners.info.crystalProd.crystalProdGui.CrystalProdGui
import _RedGold__.main.commands.user.boost.listeners.info.packageProd.packageProdGui.PackageProdGui
import _RedGold__.main.commands.user.boost.listeners.info.rankProd.rankProdGui.RankProdGui
import _RedGold__.main.commands.user.boost.listeners.selectGui.SelectGui
import _RedGold__.main.functions.task
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

@RequireListener
class InfoListener : Listener {
    @EventHandler
    fun onCloseInventory(event: InventoryCloseEvent) {
        val gui = event.inventory
        val player = event.player as Player
        val holder = gui.holder as? InfoHolder?: return

        task(1) {
            if (!player.isOnline) return@task
            if (!holder.isClose) return@task

            SelectGui().openGui(player)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        val holder = gui.holder as? InfoHolder?: return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val slot = event.slot

        when (slot) {
            11 -> {
                CrystalProdGui().openGui(player)
                holder.isClose = true
            }
            13 -> {
                RankProdGui().openGui(player)
                holder.isClose = true
            }
            15 -> {
                PackageProdGui().openGui(player)
                holder.isClose = true
            }
        }
    }
}