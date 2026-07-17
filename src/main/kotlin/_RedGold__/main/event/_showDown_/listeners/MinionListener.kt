package _RedGold__.main.event._showDown_.listeners

import _RedGold__.main.event._showDown_.managers.BattleOwner
import _RedGold__.main.event._showDown_.managers.BattleOwner.isMinion
import _RedGold__.main.functions.Gui.addPotion
import _RedGold__.main.functions.Gui.properties
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffectType


@RequireListener
@RequireJavaPlugin
class MinionListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onDamageSkipHalved(event: EntityDamageByEntityEvent) {
        val damage = event.damage

        val player = event.damager as? Player?: return
        val minion = event.entity

        val (session, _) = BattleOwner.isOwnerMinion(plugin, player, minion)?: return

        session.boss.damage(damage * 1)
        player.sendSound(Sound.ENCHANT_THORNS_HIT)
        return
    }

    @EventHandler
    fun onDieButDamage150(event: EntityDeathEvent) {
        val minion = event.entity
        val player = minion.killer?: return

        val (session, _) = BattleOwner.isOwnerMinion(plugin, player, minion)?: return

        session.boss.damage(
            minion.properties[Attribute.GENERIC_MAX_HEALTH] * 1.5
        )
        player.addPotion(PotionEffectType.STRENGTH, 10)
        player.addPotion(PotionEffectType.ABSORPTION, 20)

        if (session.minions.any { it.isValid }) return

        session.boss.damage(
            minion.properties[Attribute.GENERIC_MAX_HEALTH] * 3.0
        )
        player.addPotion(PotionEffectType.STRENGTH, 10, 1)
        player.sendSound(Sound.ENTITY_WITHER_BREAK_BLOCK)
        return
    }

    @EventHandler
    fun onPlayerRegen(event: EntityRegainHealthEvent) {
        val minion = event.entity
        isMinion(plugin, minion)?: return

        event.isCancelled = true
    }
}