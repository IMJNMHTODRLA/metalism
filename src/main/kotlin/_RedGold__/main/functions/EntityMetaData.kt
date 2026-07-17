package _RedGold__.main.functions

import _RedGold__.main.functions.NumberFormat.toUuidOrNull
import org.bukkit.entity.Entity
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.metadata.MetadataValue
import org.bukkit.plugin.java.JavaPlugin

object EntityMetaData {
    class MetadataProxy(private val plugin: JavaPlugin, private val entity: Entity) {
        operator fun contains(metadataKey: String): Boolean {
            return entity.hasMetadata(metadataKey)
        }

        operator fun get(metadataKey: String): MetadataValue? {
            return entity.getMetadata(metadataKey).firstOrNull()
        }

        operator fun set(metadataKey: String, value: Any): Boolean {
            return try {
                entity.setMetadata(metadataKey, FixedMetadataValue(plugin, value))
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    fun Entity.metaData(plugin: JavaPlugin) = MetadataProxy(plugin, this)
    fun MetadataValue.asUUID() = this.asString().toUuidOrNull()
}