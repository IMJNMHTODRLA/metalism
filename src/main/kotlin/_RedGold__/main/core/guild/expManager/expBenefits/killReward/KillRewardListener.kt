package _RedGold__.main.core.guild.expManager.expBenefits.killReward

import _RedGold__.main.core.guild.expManager.expBenefits.killRewardBenefits
import _RedGold__.main.core.guild.expManager.guildDelayAddExp
import _RedGold__.main.core.guild.expManager.guildLevelCache
import _RedGold__.main.core.guild.joinedGuildCache
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

@RequireListener
class KillRewardListener : Listener {
    @EventHandler
    fun onPlayerKill(event: EntityDeathEvent) {
        if (event.entity !is Player) return

        val attacker = event.entity.killer?: return
        val attackerUUID = attacker.uniqueId

        val guildId = joinedGuildCache[attackerUUID]?: return
        val guildLevel = guildLevelCache[guildId]?: return

        val reward = killRewardBenefits(guildLevel)
        guildDelayAddExp.merge(guildId, reward) { old, new -> old + new}
    }
}