package _RedGold__.main.core.guild.settings.leader

import _RedGold__.main.core.guild.settings.leader.delete.DeleteGui
import _RedGold__.main.core.guild.homeManager.setHome.SetHomeGui
import _RedGold__.main.core.guild.settings.leader.inviteCode.genInviteCode
import _RedGold__.main.core.guild.settings.leader.member.MemberGui
import _RedGold__.main.core.guild.settings.leader.policy.PolicyGui
import _RedGold__.main.core.guild.settings.leader.whitelist.WhitelistGui
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class LeaderListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is LeaderHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val slot = event.slot

        when(slot) {
            10 -> PolicyGui.openGui(player)
            11 -> WhitelistGui.openGui(player)
            12 -> MemberGui.openGui(player)
            13 -> DeleteGui.openGui(player)
            14 -> SetHomeGui.openGui(player)
            15 -> genInviteCode(player)
        }
    }
}