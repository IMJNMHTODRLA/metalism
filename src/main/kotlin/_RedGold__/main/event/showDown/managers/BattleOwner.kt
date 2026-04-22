package _RedGold__.main.event.showDown.managers

import _RedGold__.main.event.showDown.DataManager
import _RedGold__.main.functions.EntityMetaData
import _RedGold__.main.functions.EntityMetaData.asUUID
import _RedGold__.main.functions.EntityMetaData.metaData
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

object BattleOwner {
    fun isOwnerBoss(plugin: JavaPlugin, player: Player, boss: Entity): Pair<BossSession, EntityMetaData.MetadataProxy>? {
        val uuid = player.uniqueId

        val session = DataManager.battleSession[uuid]?: return null
        val metadata = boss.metaData(plugin)

        val ownerUuid = metadata[DataManager.OWNER_UUID]?.asUUID()
        val entityType = metadata[DataManager.TYPE_HANDEL]?.asString()

        if (ownerUuid != uuid || entityType != DataManager.TYPE_BOSS) return null
        return session to metadata
    }

    fun isOwnerMinion(plugin: JavaPlugin, player: Player, minion: Entity): Pair<BossSession, EntityMetaData.MetadataProxy>? {
        val uuid = player.uniqueId

        val session = DataManager.battleSession[uuid]?: return null
        val metadata = minion.metaData(plugin)

        val ownerUuid = metadata[DataManager.OWNER_UUID]?.asUUID()
        val entityType = metadata[DataManager.TYPE_HANDEL]?.asString()

        if (ownerUuid != uuid || entityType != DataManager.TYPE_MINION) return null
        return session to metadata
    }

    fun isOwnerAll(plugin: JavaPlugin, player: Player, all: Entity): Pair<BossSession, EntityMetaData.MetadataProxy>? {
        val uuid = player.uniqueId

        val session = DataManager.battleSession[uuid]?: return null
        val metadata = all.metaData(plugin)

        if (metadata[DataManager.OWNER_UUID]?.asUUID() != uuid) return null
        return session to metadata
    }

    fun isBoss(plugin: JavaPlugin, boss: Entity): EntityMetaData.MetadataProxy? {
        val metadata = boss.metaData(plugin)
        if (metadata[DataManager.TYPE_HANDEL]?.asString() != DataManager.TYPE_BOSS) return null
        return metadata
    }

    fun isMinion(plugin: JavaPlugin, minion: Entity): EntityMetaData.MetadataProxy? {
        val metadata = minion.metaData(plugin)
        if (metadata[DataManager.TYPE_HANDEL]?.asString() != DataManager.TYPE_MINION) return null
        return metadata
    }

    fun isAll(plugin: JavaPlugin, all: Entity): EntityMetaData.MetadataProxy? {
        val metadata = all.metaData(plugin)
        if (metadata[DataManager.TYPE_HANDEL]?.asString() !in listOf(DataManager.TYPE_BOSS, DataManager.TYPE_MINION)) return null
        return metadata
    }
}