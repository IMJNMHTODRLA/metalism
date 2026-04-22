package _RedGold__.main.event.showDown.listeners

import _RedGold__.main.event.showDown.DataManager
import _RedGold__.main.event.showDown.EventMetaDatas
import _RedGold__.main.event.showDown.managers.BattleOwner
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class BothListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler//(priority = EventPriority.MONITOR)
    fun onDamagePlus(event: EntityDamageByEntityEvent) {
        val damage = event.damage

        val monster = event.damager
        val player = event.entity as? Player?: return

        val (session, metadata) = BattleOwner.isOwnerAll(plugin, player, monster)?: return
        if (session.bossMetadata >= EventMetaDatas.BossMetaData.EXTREME) return

        val entityType = metadata[DataManager.TYPE_HANDEL]?.asString()

        if (entityType == DataManager.TYPE_BOSS) {
            event.damage = damage * 1.15
            return
        }

        if (entityType == DataManager.TYPE_MINION) {
            event.damage = damage * 1.05
            return
        }
    }
}