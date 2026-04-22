package _RedGold__.main.commands.user.ranking.listeners.selectGui

import _RedGold__.main.commands.user.ranking.Ranking
import _RedGold__.main.functions.onCommand
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class SelectListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is SelectHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val slot = event.slot
        event.isCancelled = true

        when (slot) {
            3 -> player.onCommand<Ranking>("gold")
            4 -> player.onCommand<Ranking>("boost")
            5 -> player.onCommand<Ranking>("playtime")

            12 -> player.onCommand<Ranking>("kill")
            //아 뭐 추가하지
            14 -> player.onCommand<Ranking>("kill_streak")

            21 -> player.onCommand<Ranking>("death")
            22 -> player.onCommand<Ranking>("death_streak")
        }
    }
}