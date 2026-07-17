package _RedGold__.main.commands.user.boost.listeners.info.rankProd.rankProdGui

import _RedGold__.main.commands.user.boost.listeners.info.infoGui.InfoGui
import _RedGold__.main.commands.user.boost.listeners.info.rankProd.mvpGui.MvpGui
import _RedGold__.main.commands.user.boost.listeners.info.rankProd.vipGui.VipGui
import _RedGold__.main.functions.task
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

@RequireListener
class RankProdListener : Listener {
    @EventHandler
    fun onCloseInventory(event: InventoryCloseEvent) {
        val gui = event.inventory
        val player = event.player as Player
        val holder = gui.holder as? RankProdHolder?: return

        task {
            if (!player.isOnline) return@task
            if (holder.isClose) return@task

            InfoGui().openGui(player)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        val holder = gui.holder as? RankProdHolder?: return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val slot = event.slot

        when (slot) {
            12 -> {
                holder.isClose = true
                VipGui().openGui(player)
            }

            14 -> {
                holder.isClose = true
                MvpGui().openGui(player)
            }
        }
    }
}