package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.uploadItemGui

import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.setGoodGui.SetGoodGui
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.profileGui.ProfileGui
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.isNull
import _RedGold__.main.functions.task
import _RedGold__.main.loads.RequireListener
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

@RequireListener
class UploadItemListener : Listener {
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player as? Player?: return
        val holder = event.inventory.holder as? UploadItemHolder?: return

        task(1) {
            if (!player.isOnline) return@task
            if (holder.isClose) return@task
            ProfileGui().openGui(player, holder.returnPage)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is UploadItemHolder) return

        event.isCancelled = true

        val player = event.whoClicked as Player
        val holder = gui.holder as UploadItemHolder

        if (event.clickedInventory != gui) {
            val setItem = event.currentItem

            if (setItem.isNull() || setItem.type == Material.AIR) {
                player.fail("&c아이템이 없습니다.")
                return
            }

            holder.itemData = setItem

            gui.item[13] = getItem(
                setItem.type,
                setItem.itemMeta.displayName,
                listOf("",
                    "&8&l아이템 변경을 원할 경우 다른 아이템 클릭해주세요."
                )
            )

            player.sendSound(Sound.ENTITY_PLAYER_LEVELUP)
        } else {
            val slot = event.slot

            if (slot == 18) player.closeInventory()
            else if (slot == 26) {
                holder.isClose = true
                SetGoodGui().openGui(player, holder.returnPage, holder.itemData?: run {
                    player.fail("&c&l아이템을 설정해주세요.")
                    return
                })
            }
        }
    }
}