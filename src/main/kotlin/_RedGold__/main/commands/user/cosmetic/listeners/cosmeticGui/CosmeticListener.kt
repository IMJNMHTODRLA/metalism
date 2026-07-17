package _RedGold__.main.commands.user.cosmetic.listeners.cosmeticGui

import _RedGold__.main.commands.user.cosmetic.listeners.cosmeticGui.CosmeticConst.messageList
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.FastNumber.seconds
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.task
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.playerData.data
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class CosmeticListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is CosmeticHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val holder = event.inventory.holder as CosmeticHolder
        val cosmeticEnum = holder.cosmeticEnum

        val clickType = event.click
        val slot = event.slot

        if (slot in 0..44) {
            if (clickType.isLeftClick) {
                if (!player.data.hasCosmetic(slot, cosmeticEnum)) return

                if (player.data.setCosmetic(slot, cosmeticEnum, true))
                    player.good("&a장착이 완료되었습니다.")
                else
                    player.fail("&c장착에 실패하였습니다.")
            } else if (clickType.isRightClick) {
                val displayName = CosmeticConst.getDisplayName(slot, cosmeticEnum)
                val rankPrefix = PermissionEnum[player].prefix
                val name = player.name

                player.good("$displayName $rankPrefix $name&f: ${messageList.random()}")
                player.closeInventory()

                task(1.seconds) {
                    if (player.isOnline) player.inv + holder.inventory
                }
            }
            return
        }

        //TODO: 나중에 페이지 이동 만들자
    }
}