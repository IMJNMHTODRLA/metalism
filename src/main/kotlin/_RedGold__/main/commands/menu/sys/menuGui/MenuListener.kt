package _RedGold__.main.commands.menu.sys.menuGui

import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class MenuListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is MenuHolder) {
            val player = event.whoClicked as Player
            val slot = event.slot
            event.isCancelled = true

            when (slot) {
                10 -> player.performCommand("rtp")
                11 -> player.performCommand("shop")
                12 -> player.performCommand("ranking")
                13 -> player.performCommand("chest")
                14 -> player.performCommand("boost")
                15 -> player.performCommand("betting")
                16 -> player.performCommand("home")

                19 -> player.performCommand("back")
                20 -> player.performCommand("event")
                21 -> player.performCommand("mission daily")
                22 -> player.performCommand("style")
                23 -> player.performCommand("ec")
                24 -> player.performCommand("discord")
            }
        }
    }
}