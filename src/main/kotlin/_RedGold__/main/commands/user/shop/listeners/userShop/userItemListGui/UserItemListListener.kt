package _RedGold__.main.commands.user.shop.listeners.userShop.userItemListGui

import _RedGold__.main.commands.user.shop.listeners.userShop.UserGlobalConst
import _RedGold__.main.commands.user.shop.listeners.userShop.userItemBuyGui.UserItemBuyGui
import _RedGold__.main.commands.user.shop.listeners.userShop.userItemInfoGui.UserItemInfoGui
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.profileGui.ProfileGui
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.FastBoolean.trueRun
import _RedGold__.main.functions.isNegative
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class UserItemListListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is UserItemListHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val holder = gui.holder as UserItemListHolder
        val clickType = event.click
        val slot = event.slot
        val page = holder.page

        when(slot) {
            46 -> if (holder.page > 0) UserItemListGui().openGui(player, page - 1)
            53 -> UserItemListGui().openGui(player, page + 1)

            50 -> ProfileGui().openGui(player, page)

            else -> {
                val getId = UserItemListConst.getIdFromSlot(page, slot)
                getId.isNegative.trueRun { return }

                val data = UserGlobalConst.allUserShopData.getOrNull(getId)?: return
                (data.isCollect || data.isPurchase).trueRun {
                    player.sendMsg("&c만료된 아이템입니다.")
                    return
                }

                when (clickType) {
                    ClickType.LEFT -> UserItemBuyGui().openGui(player, page, getId)
                    ClickType.RIGHT -> UserItemInfoGui().openGui(player, page, getId) //상세정보
                    else -> {}
                }
            }
        }
    }
}