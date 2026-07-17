package _RedGold__.main.commands.user.boost.listeners.settings.settingsGui

import _RedGold__.main.commands.user.boost.listeners.settings.vip.vipGui.VipGui
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

        when (event.slot) {
            12 -> VipGui().openGui(player)
            14 -> { /* TODO: 추후에 만들기 */ }
        }
    }
}