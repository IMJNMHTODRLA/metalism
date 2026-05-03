package _RedGold__.main.commands.user.boost.listeners.info.packageProd.starterGui

import _RedGold__.main.commands.user.boost.listeners.info.InfoGlobalConst
import _RedGold__.main.commands.user.boost.listeners.info.packageProd.packageProdGui.PackageProdGui
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.dataManager.BoostData
import _RedGold__.main.managers.playerData.variableManager.BoostEnum
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class StarterListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onCloseInventory(event: InventoryCloseEvent) {
        val gui = event.inventory
        val player = event.player as Player
        if (gui.holder !is StarterHolder) return

        plugin.task {
            if (!player.isOnline) return@task
            PackageProdGui().openGui(player)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is StarterHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        if (event.slot != 12) return

        val player = event.whoClicked as Player

        InfoGlobalConst.buy(
            player, "스타터 패키지", StarterConst.PRICE,
            { StarterConst.pack.isLimitReach(player) }
        ) {
            val boostData = data.boostMap.getOrPut(StarterConst.enum) { BoostData(0, 0L) }

            data.gold += StarterConst.pack.giveGold
            data.crystal += StarterConst.pack.giveCrystal

            StarterConst.pack.giveItem.forEach { inv += it }

            boostData.amount += 1
            boostData.expirationAt = now + StarterConst.pack.period
        }
    }
}