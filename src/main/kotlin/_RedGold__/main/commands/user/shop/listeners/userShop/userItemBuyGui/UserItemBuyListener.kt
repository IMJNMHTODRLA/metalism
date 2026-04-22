package _RedGold__.main.commands.user.shop.listeners.userShop.userItemBuyGui

import _RedGold__.main.commands.user.shop.listeners.userShop.UserGlobalConst
import _RedGold__.main.commands.user.shop.listeners.userShop.userItemListGui.UserItemListGui
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.Cubic.then
import _RedGold__.main.functions.FastBoolean.trueRun
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.functions.Scheduler.taskAsync
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.functions.isNegative
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.mailBoxManager.MailBoxData
import _RedGold__.main.managers.mailBoxManager.sendMail
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.userShopManager.UserShopGoodsEnum
import _RedGold__.main.managers.userShopManager.FEE_PERCENT
import _RedGold__.main.managers.userShopManager.MAIL_EXPIRE_PERIOD
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class UserItemBuyListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player as? Player?: return
        val holder = event.inventory.holder as? UserItemBuyHolder?: return
        if (holder.isBuy) return //true는 넘겨, false는 붙잡기

        plugin.task(1) {
            if (!player.isOnline) return@task
            if (holder.isBuy) return@task

            UserItemListGui().openGui(player, holder.returnPage)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is UserItemBuyHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val holder = gui.holder as UserItemBuyHolder
        val slot = event.slot

        when(slot) {
            15 -> player.closeInventory()

            11 -> {
                holder.isBuy = true

                val index = UserGlobalConst.allUserShopData.indexOfFirst { it.id == holder.itemData.id }
                index.isNegative.trueRun {
                    player.fail("&c아이템을 찾을 수 없습니다.")
                    UserItemListGui().openGui(player, holder.returnPage)
                    return
                }

                val userShopData = UserGlobalConst.allUserShopData[index]
                if (userShopData.isPurchase) {
                    player.fail("&c다른 플레이어가 이미 아이템을 구매하였습니다.")
                    player.closeInventory()
                    return
                }

                val priceType = holder.itemData.priceType
                val priceName = priceType.displayName

                val priceAmount = holder.itemData.priceAmount
                val fee = (priceAmount * FEE_PERCENT).toInt()

                val priceTotalAmount = fee + priceAmount

                if (priceType[player.data] < priceTotalAmount) {
                    player.fail("&c$priceName(이)가 부족합니다. 필요 $priceName: ${(priceTotalAmount - priceType[player.data]).toFormat()} $priceName")
                    return
                }

                if (!player.inv.hasSpace(userShopData.displayMaterial, userShopData.displayAmount)) {
                    player.fail("&c인벤토리 공간이 부족합니다.")
                    return
                }

                userShopData.isPurchase = true
                priceType[player.data] -= priceTotalAmount

                player.inv += holder.itemData.item
                plugin.taskAsync {
                    sendMail(
                        MailBoxData(
                            0, userShopData.uuid,
                            "&e&l유저상점", "&f&l아이템 판매", "${userShopData.displayName} &f&l아이템이 판매되었습니다.",
                            null,
                            (priceType == UserShopGoodsEnum.GOLD) then priceAmount,
                            (priceType == UserShopGoodsEnum.CRYSTAL) then priceAmount.toInt(),
                            now, MAIL_EXPIRE_PERIOD,
                        )
                    )
                }

                player.good("${userShopData.displayName}&a을(를) 구매했습니다.")
            }
        }
    }
}