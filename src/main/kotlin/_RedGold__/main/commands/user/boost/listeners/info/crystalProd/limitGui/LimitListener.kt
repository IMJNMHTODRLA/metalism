package _RedGold__.main.commands.user.boost.listeners.info.crystalProd.limitGui

import _RedGold__.main.commands.user.boost.listeners.info.InfoGlobalConst
import _RedGold__.main.commands.user.boost.listeners.info.crystalProd.crystalProdGui.CrystalProdGui
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
class LimitListener : Listener {
    @EventHandler
    fun onCloseInventory(event: InventoryCloseEvent) {
        val gui = event.inventory
        val player = event.player as Player
        if (gui.holder !is LimitHolder) return

        task {
            if (!player.isOnline) return@task
            CrystalProdGui().openGui(player)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is LimitHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return
        if (event.slot != 12) return

        val player = event.whoClicked as Player
        val round = LimitConst.nowRound(player)

        InfoGlobalConst.buy(
            player, "한정 판매 크리스탈", LimitConst.price[round],
            { LimitConst.pack.isLimitReach(player) }
        ) {
            val boostData = data.boostMap.getOrPut(LimitConst.enum) { BoostData(0, 0L) }
            boostData.amount += 1
            boostData.expirationAt = now + LimitConst.pack.period

            data.crystal += LimitConst.pack.giveCrystal[round]
            LimitGui().openGui(player)
        }
    }
}