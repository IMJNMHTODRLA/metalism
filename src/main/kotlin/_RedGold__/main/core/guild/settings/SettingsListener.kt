package _RedGold__.main.core.guild.settings

import _RedGold__.main.core.guild.settings.leader.LeaderGui
import _RedGold__.main.core.guild.settings.member.MemberGui
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class SettingsListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is SettingsHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val slot = event.slot

        when(slot) {
            12 -> LeaderGui.openGui(player)
            14 -> MemberGui.openGui(player)
        }
    }
}