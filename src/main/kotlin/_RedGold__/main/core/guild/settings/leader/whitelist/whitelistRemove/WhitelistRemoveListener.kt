package _RedGold__.main.core.guild.settings.leader.whitelist.whitelistRemove

import _RedGold__.main.core.guild.settings.leader.whitelist.removeWhitelistData
import _RedGold__.main.core.guild.playerCooldownMsg
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class WhitelistRemoveListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        val holder = gui.holder as? WhitelistRemoveHolder?: return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        if (event.slot != 13) return

        if (playerCooldownMsg(player)) return

        player.closeInventory()
        player.good("&c&l${holder.target.name}님을 화이트리스트에 삭제하였습니다.")

        taskAsync {
            removeWhitelistData(holder.data)
        }
    }
}