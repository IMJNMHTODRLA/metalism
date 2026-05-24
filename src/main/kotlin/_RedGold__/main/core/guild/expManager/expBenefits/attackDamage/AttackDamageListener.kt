package _RedGold__.main.core.guild.expManager.expBenefits.attackDamage

import _RedGold__.main.core.guild.expManager.expBenefits.attackDamageBenefits
import _RedGold__.main.core.guild.expManager.guildLevelCache
import _RedGold__.main.core.guild.joinedGuildCache
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

@RequireListener
class AttackDamageListener : Listener {
    @EventHandler
    fun onAttack(event: EntityDamageByEntityEvent) {
        val attacker = event.damager as? Player?: return
        val attackerUUID = attacker.uniqueId

        val guildId = joinedGuildCache[attackerUUID]?: return
        val guildLevel = guildLevelCache[guildId]?: return

        val damageMultiply = attackDamageBenefits(guildLevel)
        event.damage *= (1.0 + damageMultiply)
    }
}