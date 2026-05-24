package _RedGold__.main.commands.mvp.fly

import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.SPAWN_WORLD
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent

@RequireListener
class FlyListener : Listener {
    @EventHandler
    fun onWorldChange(event: PlayerChangedWorldEvent) {
        val player = event.player

        if (player.gameMode == GameMode.CREATIVE) return

        if (player.world.name != SPAWN_WORLD) player.isFlying = false
    }
}