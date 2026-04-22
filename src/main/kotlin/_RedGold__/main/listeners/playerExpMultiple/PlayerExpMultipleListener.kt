package _RedGold__.main.listeners.playerExpMultiple

import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.variableManager.BoostEnum
import _RedGold__.main.managers.playerData.variableManager.BoostSealed
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerExpChangeEvent

@RequireListener
class PlayerExpMultipleListener : Listener {
    @EventHandler
    fun onExpChange(event: PlayerExpChangeEvent) {
        val player = event.player
        val giveExp = event.amount

        val monthlyPackageData = player.data.boostMap[BoostEnum.MONTHLY_PACKAGE]
        val expirationAt = monthlyPackageData?.expirationAt?: 0L
        val remainingPeriod = expirationAt - now

        if (remainingPeriod > 0) event.amount = (
            giveExp * BoostSealed.MonthlyPackage().multipleExp
        ).toInt()
    }
}