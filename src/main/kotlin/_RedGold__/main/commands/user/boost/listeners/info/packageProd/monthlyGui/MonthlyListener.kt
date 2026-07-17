package _RedGold__.main.commands.user.boost.listeners.info.packageProd.monthlyGui

import _RedGold__.main.commands.user.boost.listeners.info.InfoGlobalConst
import _RedGold__.main.commands.user.boost.listeners.info.packageProd.packageProdGui.PackageProdGui
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.functions.task
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.dataManager.BoostData
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

@RequireListener
class MonthlyListener : Listener {
    @EventHandler
    fun onCloseInventory(event: InventoryCloseEvent) {
        val gui = event.inventory
        val player = event.player as Player
        if (gui.holder !is MonthlyHolder) return

        task {
            if (!player.isOnline) return@task
            PackageProdGui().openGui(player)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is MonthlyHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        if (event.slot != 12) return

        val player = event.whoClicked as Player

        InfoGlobalConst.buy(
            player, "월간 패키지", MonthlyConst.PRICE,
            { MonthlyConst.pack.isLimitReach(player) }
        ) {
            val boostData = data.boostMap.getOrPut(MonthlyConst.enum) { BoostData(0, 0L) }
            boostData.amount += 1
            boostData.expirationAt = now + MonthlyConst.pack.period

            data.crystal += MonthlyConst.pack.GIVE_CRYSTAL
        }
    }
}