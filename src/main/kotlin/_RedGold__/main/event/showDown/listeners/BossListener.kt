package _RedGold__.main.event.showDown.listeners

import _RedGold__.main.event.showDown.EventMetaDatas
import _RedGold__.main.event.showDown.managers.BattleOwner
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class BossListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onDamageReflection(event: EntityDamageByEntityEvent) {
        val damage = event.damage

        val player = event.damager as? Player?: return
        val monster = event.entity

        val (session, _) = BattleOwner.isOwnerBoss(plugin, player, monster)?: return
        if (session.bossMetadata < EventMetaDatas.BossMetaData.HARD) return

        player.damage(damage * 0.08, monster)
        player.sendSound(Sound.ENCHANT_THORNS_HIT)
        return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onDamageHalved(event: EntityDamageByEntityEvent) {
        val damage = event.damage

        val player = event.damager as? Player?: return
        val monster = event.entity

        val (session, _) = BattleOwner.isOwnerBoss(plugin, player, monster)?: return
        if (session.minions.none { it.isValid }) return

        player.damage(damage * 0.5, monster)
        player.sendSound(Sound.ENCHANT_THORNS_HIT)

        event.damage = damage * 0.5
        return
    }
}