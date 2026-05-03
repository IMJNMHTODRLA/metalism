package _RedGold__.main.commands.user.boost.listeners.info.rankProd.mvpGui

import _RedGold__.main.commands.user.boost.listeners.info.InfoGlobalConst
import _RedGold__.main.commands.user.boost.listeners.info.rankProd.rankProdGui.RankProdGui
import _RedGold__.main.functions.EasyPermission.permission
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.playerData.data
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class MvpListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onCloseInventory(event: InventoryCloseEvent) {
        val gui = event.inventory
        val player = event.player as Player
        if (gui.holder !is MvpHolder) return

        plugin.task {
            if (!player.isOnline) return@task
            RankProdGui().openGui(player)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is MvpHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        if (event.slot != 12) return

        val player = event.whoClicked as Player
        val uuid = player.uniqueId

        InfoGlobalConst.buy(
            player, "${MvpConst.giveRank.name} 랭크", MvpConst.PRICE,
            { player.permission(MvpConst.giveRank) }
        ) {
            PermissionEnum.modify(uuid, MvpConst.giveRank)

            data.gold += MvpConst.BONUS_GOLD
            data.crystal += MvpConst.BONUS_CRYSTAL
            player.inv += MvpConst.bonusItem
        }
    }
}