package _RedGold__.main.event._showDown_.listeners

import _RedGold__.main.event._showDown_.DataManager
import _RedGold__.main.event._showDown_.managers.BattleOwner
import _RedGold__.main.function.Data.getDataUuid
import _RedGold__.main.function.Data.hasDataUuid
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.functions.EntityMetaData.asUUID
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class PlayerListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val player = event.player
        val uuid = player.uniqueId

        val session = DataManager.battleSession[uuid]
        session?.defeatQuit()

        val point = DataManager.point.remove(uuid)?: 0L
        val bestPoint = DataManager.bestPoint.remove(uuid)?: 0L
        val ticket = DataManager.ticket.remove(uuid)?: 0
        val cachingDifficulty = DataManager.cachingDifficulty.remove(uuid)?: 0
        val cachingPoint = DataManager.cachingPoint.remove(uuid)?: 0L

        saveData(plugin, player, DataManager.POINT_PATH, point)
        saveData(plugin, player, DataManager.BEST_POINT_PATH, bestPoint)

        saveData(plugin, player, DataManager.TICKET_PATH, ticket)

        saveData(plugin, player, DataManager.CACHE_DIFFICULTY_PATH, cachingDifficulty)
        saveData(plugin, player, DataManager.CACHE_POINT_PATH, cachingPoint)
    }

    @EventHandler
    fun onJoin(event: AsyncPlayerPreLoginEvent) {
        val uuid = event.uniqueId

        if (hasDataUuid(plugin, uuid, DataManager.POINT_PATH)) {
            DataManager.point[uuid] = getDataUuid(plugin, uuid, DataManager.POINT_PATH).toLong()
            DataManager.bestPoint[uuid] = getDataUuid(plugin, uuid, DataManager.BEST_POINT_PATH).toLong()

            DataManager.ticket[uuid] = getDataUuid(plugin, uuid, DataManager.TICKET_PATH).toInt()

            DataManager.cachingDifficulty[uuid] = getDataUuid(plugin, uuid, DataManager.CACHE_DIFFICULTY_PATH).toInt()
            DataManager.cachingPoint[uuid] = getDataUuid(plugin, uuid, DataManager.CACHE_POINT_PATH).toLong()
        }
    }

    @EventHandler
    fun bossKill(event: EntityDeathEvent) {
        val boss = event.entity

        val metadata = BattleOwner.isBoss(plugin, boss)?: return

        val session = DataManager.battleSession[
            metadata[DataManager.OWNER_UUID]?.asUUID()
        ]
        session?.victory()
    }

    @EventHandler
    fun imDieByBossNo(event: PlayerDeathEvent) {
        val player = event.entity
        val uuid = player.uniqueId

        val session = DataManager.battleSession[uuid]
        session?.defeat()
    }
}