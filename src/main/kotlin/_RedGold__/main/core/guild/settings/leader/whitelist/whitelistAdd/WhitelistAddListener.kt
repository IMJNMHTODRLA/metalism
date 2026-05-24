package _RedGold__.main.core.guild.settings.leader.whitelist.whitelistAdd

import _RedGold__.main.core.guild.settings.leader.whitelist.addWhitelistData
import _RedGold__.main.core.guild.playerCooldownMsg
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class WhitelistAddListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        val holder = gui.holder as? WhitelistAddHolder?: return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        if (event.slot != 13) return

        if (playerCooldownMsg(player)) return

        player.closeInventory()
        player.good("&a&l${holder.target.name}님을 화이트리스트에 추가하였습니다.")

        taskAsync {
            addWhitelistData(holder.data)
        }
    }
}