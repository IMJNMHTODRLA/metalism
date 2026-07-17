package _RedGold__.main.listeners.playerSwapItem

import _RedGold__.main.commands.user.menu.Menu
import _RedGold__.main.functions.onCommand
import _RedGold__.main.loads.RequireListener
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerSwapHandItemsEvent

@RequireListener
class PlayerSwapItemListener : Listener {
    @EventHandler
    fun onSwap(event: PlayerSwapHandItemsEvent) {
        val player = event.player
        if (player.isSneaking) {
            player.onCommand<Menu>()
            event.isCancelled = true
        }

        //TODO: 나중에 그냥 F 할 시 만일 카트리지이면 카트리지 스킬 업글 창이나 그런거 뜨게 하기
    }
}