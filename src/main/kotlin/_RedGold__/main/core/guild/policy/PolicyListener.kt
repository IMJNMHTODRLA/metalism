package _RedGold__.main.core.guild.policy

import _RedGold__.main.core.guild.joinedGuildCache
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class PolicyListener : Listener {
    @EventHandler
    fun onPlayerAttack(event: EntityDamageByEntityEvent) {
        val victim = event.entity as? Player?: return
        val attacker = event.damager as? Player?: return

        val victimGuild = joinedGuildCache[victim.uniqueId]
        val attackerGuild = joinedGuildCache[attacker.uniqueId]

        if (
            victimGuild == attackerGuild &&
            victimGuild !in isAllowPvpGuilds
        ) event.isCancelled = true
    }
}