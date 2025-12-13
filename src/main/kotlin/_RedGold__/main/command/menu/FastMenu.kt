package _RedGold__.main.command.menu

import _RedGold__.main.load.RequireListener
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerSwapHandItemsEvent

@RequireListener
class FastMenu : Listener {
    @EventHandler
    fun onSwap(event: PlayerSwapHandItemsEvent) {
        val player = event.player
        if (player.isSneaking) {
            player.performCommand("menu")
            event.isCancelled = true
        }
    }
}