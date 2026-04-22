package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.confirmGui

import _RedGold__.main.commands.user.shop.listeners.userShop.UserGlobalConst
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.setGoodGui.SetGoodGui
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.profileGui.ProfileGui
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.functions.isNull
import _RedGold__.main.managers.userShopManager.UserShopDetailData
import _RedGold__.main.managers.userShopManager.UserShopEntry
import _RedGold__.main.managers.userShopManager.UserShopInfoData
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.plugin.java.JavaPlugin

class ConfirmListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player as? Player?: return
        val holder = event.inventory.holder as? ConfirmHolder?: return

        plugin.task(1) {
            if (!player.isOnline) return@task
            if (holder.isClose) return@task
            ProfileGui().openGui(player, holder.returnPage)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is ConfirmHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val uuid = player.uniqueId
        val holder = gui.holder as ConfirmHolder
        val slot = event.slot

        if (slot == 26) {
            if (!player.inv.hasAtLeast(holder.itemData, holder.itemData.amount)) {
                player.fail("&c아이템이 부족합니다.")
                return
            }

            player.closeInventory()
            player.inv -= holder.itemData

            val data = UserShopEntry(
                UserShopInfoData(
                    -1, uuid, -1,
                    holder.itemData.type, holder.itemData.itemMeta.displayName, holder.itemData.itemMeta.hasEnchants(), holder.itemData.amount
                ),
                UserShopDetailData(
                    -1, uuid, holder.type,
                    holder.price, holder.itemData
                )
            )

            UserGlobalConst.newUserItemData.getOrPut(uuid) { mutableListOf() }.add(data)
            player.good("&a아이템 업로드가 완료됐습니다!")
        } else {
            holder.isClose = true
            SetGoodGui().openGui(player, holder.returnPage, holder.itemData, holder.type, holder.price)
        }
    }
}