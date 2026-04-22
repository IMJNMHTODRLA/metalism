package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.collectItemGui

import _RedGold__.main.commands.user.shop.listeners.userShop.UserGlobalConst
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.reggedListGui.ReggedListGui
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.functions.ifRun
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.data
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class CollectItemListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player as? Player?: return
        val holder = event.inventory.holder as? CollectItemHolder?: return

        plugin.task(1) {
            if (!player.isOnline) return@task
            ReggedListGui().openGui(player, holder.returnPage)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is CollectItemHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val uuid = player.uniqueId

        val holder = gui.holder as CollectItemHolder

        val detail = holder.detail?: return
        val slot = event.slot

        if (slot == 11) {
            val isRemove = (detail.id == -1)
                .ifRun { UserGlobalConst.newUserItemData[uuid]?.removeIf { it.detail == detail }?: false }
                .elseRun {
                    UserGlobalConst.allUserShopData
                        .find { it.id == detail.id && it.isCollect && !it.isPurchase && !it.isDelete }
                        ?.let { it.isPurchase = true; true }?: false
                }

            if (!isRemove) {
                player.fail("&c회수에 실패하였습니다.")
                return
            }

            player.inv += detail.item
            detail.priceType[player.data] += detail.priceAmount

            player.good("&c회수가 완료되었습니다.")
            player.closeInventory()
            return
        }

        if (slot == 15) player.closeInventory()
    }
}