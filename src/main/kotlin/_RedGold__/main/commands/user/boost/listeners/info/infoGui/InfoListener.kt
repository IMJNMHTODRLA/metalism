package _RedGold__.main.commands.user.boost.listeners.info.infoGui

import _RedGold__.main.commands.user.boost.listeners.info.packageProd.packageProdGui.PackageProdGui
import _RedGold__.main.commands.user.boost.listeners.info.rankProd.rankProdGui.RankProdGui
import _RedGold__.main.commands.user.boost.listeners.selectGui.SelectGui
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
class InfoListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onCloseInventory(event: InventoryCloseEvent) {
        val gui = event.inventory
        val player = event.player as Player
        val holder = gui.holder as? InfoHolder?: return

        plugin.task {
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
                //TODO: 크리스탈 그거 상품 열리게 만들기
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