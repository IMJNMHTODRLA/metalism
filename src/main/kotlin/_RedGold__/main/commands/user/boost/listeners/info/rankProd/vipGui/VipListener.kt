package _RedGold__.main.commands.user.boost.listeners.info.rankProd.vipGui

import _RedGold__.main.commands.user.boost.listeners.info.InfoGlobalConst
import _RedGold__.main.commands.user.boost.listeners.info.rankProd.rankProdGui.RankProdGui
import _RedGold__.main.functions.EasyPermission.permission
import _RedGold__.main.functions.task
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.playerData.data
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

@RequireListener
class VipListener : Listener {
    @EventHandler
    fun onCloseInventory(event: InventoryCloseEvent) {
        val gui = event.inventory
        val player = event.player as Player
        if (gui.holder !is VipHolder) return

        task {
            if (!player.isOnline) return@task
            RankProdGui().openGui(player)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is VipHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        if (event.slot != 12) return

        val player = event.whoClicked as Player
        val uuid = player.uniqueId

        InfoGlobalConst.buy(
            player, "${VipConst.giveRank.name} 랭크", VipConst.PRICE,
            { player.permission(VipConst.giveRank) }
        ) {
            PermissionEnum.modify(uuid, VipConst.giveRank)
            data.gold += VipConst.BONUS_GOLD
            data.crystal += VipConst.BONUS_CRYSTAL
        }
    }
}