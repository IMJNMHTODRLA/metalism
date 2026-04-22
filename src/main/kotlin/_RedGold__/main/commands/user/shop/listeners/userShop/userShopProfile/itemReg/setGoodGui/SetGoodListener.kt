package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.setGoodGui

import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.confirmGui.ConfirmGui
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.uploadItemGui.UploadItemGui
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.profileGui.ProfileGui
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.PlusMath.pow
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.functions.isZero
import _RedGold__.main.managers.userShopManager.UserShopGoodsEnum
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.plugin.java.JavaPlugin

class SetGoodListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player as? Player?: return
        val holder = event.inventory.holder as? SetGoodHolder?: return

        plugin.task(1) {
            if (!player.isOnline) return@task
            if (holder.isClose) return@task
            ProfileGui().openGui(player, holder.returnPage)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is SetGoodHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val holder = gui.holder as SetGoodHolder
        val slot = event.slot

        when(slot) {
            12, 14 -> {
                holder.type = holder.type.next()
                holder.price = 0L

                gui.item[12] = getItem(Material.GOLD_INGOT, "&6&골드").apply { if (holder.type == UserShopGoodsEnum.GOLD) enchantEffect() }
                gui.item[14] = getItem(Material.DIAMOND, "&b&크리스탈").apply { if (holder.type == UserShopGoodsEnum.CRYSTAL) enchantEffect() }

                gui.item[22] = getItem(
                    Material.BLACK_STAINED_GLASS_PANE,
                    SetGoodConst.currentName(holder.price, holder.type)
                )

                repeat(4) { i ->
                    val good = SetGoodConst.DefaultGood(holder.type) * 10.pow(i)

                    gui.item[21 - i] = getItem(
                        Material.RED_STAINED_GLASS_PANE,
                        SetGoodConst.removeName(good, holder.type),
                        SetGoodConst.setLore(good, "차감")
                    )

                    gui.item[23 + i] = getItem(
                        Material.GREEN_STAINED_GLASS_PANE,
                        SetGoodConst.addName(good, holder.type),
                        SetGoodConst.setLore(good, "추가")
                    )
                }
            }

            in 18..21, in 23..26 -> {
                val change = when(slot) {
                    in 18..21 -> -(SetGoodConst.DefaultGood(holder.type) * 10.pow(21 - slot)) // 차감은 음수로
                    in 23..26 -> SetGoodConst.DefaultGood(holder.type) * 10.pow(slot - 23) // 추가는 양수로
                    else -> return
                }
                val newPrice = holder.price + change

                if (newPrice < 0) {
                    player.fail("&c가격을 음수로 내릴 수 없습니다.")
                    return
                }

                if (newPrice > SetGoodConst.MaxGood(holder.type)) {
                    player.fail("&c가격을 더 이상 높힐 수 없습니다.")
                    return
                }

                holder.price = newPrice

                gui.item[22] = getItem(
                    Material.BLACK_STAINED_GLASS_PANE,
                    SetGoodConst.currentName(holder.price, holder.type)
                )

                if (change < 0) {
                    player.sendMsg(SetGoodConst.removeMessage(newPrice, change, holder.type))
                    player.sendSound(Sound.ENTITY_ENDERMAN_TELEPORT, 2f)
                } else {
                    player.sendMsg(SetGoodConst.addMessage(newPrice, change, holder.type))
                    player.sendSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f)
                }
            }

            27, 35 -> {
                holder.isClose = true

                if (slot == 27) UploadItemGui().openGui(player, holder.returnPage, holder.itemData)
                else {
                    if (holder.price.isZero) player.fail("&c가격이 0 초과여야 합니다.")
                    ConfirmGui().openGui(player, holder.returnPage, holder.itemData, holder.type, holder.price)
                }
            }
        }
    }
}