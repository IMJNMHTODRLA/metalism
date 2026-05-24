package _RedGold__.main.core.guild.expManager.expBenefits.fallDamageDec

import _RedGold__.main.core.guild.expManager.expBenefits.failDamageDecBenefits
import _RedGold__.main.core.guild.expManager.guildLevelCache
import _RedGold__.main.core.guild.joinedGuildCache
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

@RequireListener
class FallDamageDecListener : Listener {
    @EventHandler
    fun onFailDamage(event: EntityDamageEvent) {
        if (event.cause != EntityDamageEvent.DamageCause.FALL) return

        val player = event.entity as? Player?: return
        val uuid = player.uniqueId

        val guildId = joinedGuildCache[uuid]?: return
        val guildLevel = guildLevelCache[guildId]?: return

        val damageDec = failDamageDecBenefits(guildLevel)
        event.damage *= (1.0 - damageDec)
    }
}