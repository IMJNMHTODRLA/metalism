package _RedGold__.main.sys

import _RedGold__.main.Main.Boost.monthlySubData
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.sys.ExpMultiple.ExpMultipleData.normalPlayer
import _RedGold__.main.sys.ExpMultiple.ExpMultipleData.subPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerExpChangeEvent

@RequireListener
class ExpMultiple : Listener {
    object ExpMultipleData {
        var subPlayer = 1.0
        var normalPlayer = 1.0
    }

    @EventHandler
    fun onExpChange(event: PlayerExpChangeEvent) {
        val uuid = event.player.uniqueId
        val giveExp = event.amount

        val multiple = if ((monthlySubData[uuid]?: 0L) > System.currentTimeMillis() / 1000) subPlayer else normalPlayer
        event.amount = (giveExp * multiple).toInt()
    }
}