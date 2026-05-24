package _RedGold__.main.core.guild.settings.member

import _RedGold__.main.core.guild.settings.member.donate.DonateGui
import _RedGold__.main.core.guild.settings.member.stats.StatsGui
import _RedGold__.main.core.guild.shareChestManager.shareChestInv.ShareChestInvGui
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class MemberListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is MemberHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val slot = event.slot

        when(slot) {
            10 -> DonateGui.openGui(player)
            11 -> StatsGui.openGui(player)
            12 -> ShareChestInvGui.openGui(player)
            13 -> DeleteGui.openGui(player)
        }
    }
}