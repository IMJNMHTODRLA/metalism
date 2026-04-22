package _RedGold__.main.event.showDown.managers

import _RedGold__.main.event.showDown.DataManager
import _RedGold__.main.event.showDown.EventMetaDatas
import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.Gui.baseMaxHealth
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.properties
import org.bukkit.attribute.Attribute
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.entity.Zombie
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin

class BossEntity(private val plugin: JavaPlugin) {
    fun bossSpawn(
        player: Player,
        bossMetadata: EventMetaDatas.BossMetaData
    ): Zombie {
        val location = player.location
        val boss = player.world.spawnEntity(
        location.add(location.direction.multiply(10)).apply {
            y = (world?.getHighestBlockYAt(this)?.toDouble()?: y) + 20
        }, EntityType.ZOMBIE) as Zombie

        return boss.apply {
            properties[Attribute.GENERIC_SCALE] = 3.0
            properties[Attribute.GENERIC_MOVEMENT_SPEED] = 0.3
            properties[Attribute.GENERIC_GRAVITY] = 0.05
            properties[Attribute.GENERIC_JUMP_STRENGTH] = 1.0
            properties[Attribute.GENERIC_FOLLOW_RANGE] = 1000.0
            properties[Attribute.GENERIC_KNOCKBACK_RESISTANCE] = 1000.0
            properties[Attribute.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE] = 1000.0
            properties[Attribute.GENERIC_SAFE_FALL_DISTANCE] = 5000.0

            properties[Attribute.GENERIC_ARMOR] = bossMetadata.defense
            properties[Attribute.GENERIC_ATTACK_DAMAGE] = bossMetadata.damage

            baseMaxHealth = bossMetadata.hp
            health = bossMetadata.hp

            setGravity(true)
            removeWhenFarAway = false
            isCustomNameVisible = true
            customName = gc("&e&l${DataManager.BOSS_NAME_ASCII}")
            isBaby = false
            canPickupItems = false
            target = player

            setMetadata(
                DataManager.TYPE_HANDEL,
                FixedMetadataValue(plugin, DataManager.TYPE_BOSS)
            )

            setMetadata(
                DataManager.OWNER_UUID,
                FixedMetadataValue(plugin, player.uniqueId)
            )

            equipment.setItemInOffHand(null)
            equipment.itemInMainHandDropChance = 0.0f
            equipment.itemInOffHandDropChance = 0.0f

            equipment.helmet = null
            equipment.chestplate = null
            equipment.leggings = null
            equipment.boots = null
            equipment.helmetDropChance = 0.0f
            equipment.chestplateDropChance = 0.0f
            equipment.leggingsDropChance = 0.0f
            equipment.bootsDropChance = 0.0f
        }
    }

    fun minionSpawn(
        player: Player,
        boss: Zombie,
        minionMetadata: EventMetaDatas.MinionMetaData
    ): Zombie {
        val minion = player.world.spawnEntity(boss.location, EntityType.ZOMBIE) as Zombie

        return minion.apply {
            properties[Attribute.GENERIC_SCALE] = 0.8
            properties[Attribute.GENERIC_MOVEMENT_SPEED] = 0.345
            properties[Attribute.GENERIC_FOLLOW_RANGE] = 1000.0
            properties[Attribute.GENERIC_KNOCKBACK_RESISTANCE] = 1000.0
            properties[Attribute.GENERIC_EXPLOSION_KNOCKBACK_RESISTANCE] = 1000.0
            properties[Attribute.GENERIC_SAFE_FALL_DISTANCE] = 5000.0

            properties[Attribute.GENERIC_ATTACK_DAMAGE] = minionMetadata.damage
            baseMaxHealth = minionMetadata.hp
            health = minionMetadata.hp

            setGravity(true)
            setAdult()
            removeWhenFarAway = false
            isCustomNameVisible = true
            customName = gc("&e&l${DataManager.BOSS_NAME_ASCII} MINION")
            canPickupItems = false
            target = player

            setMetadata(
                DataManager.TYPE_HANDEL,
                FixedMetadataValue(plugin, DataManager.TYPE_MINION)
            )

            setMetadata(
                DataManager.OWNER_UUID,
                FixedMetadataValue(plugin, player.uniqueId)
            )

            equipment.setItemInOffHand(null)
            equipment.itemInMainHandDropChance = 0.0f
            equipment.itemInOffHandDropChance = 0.0f

            equipment.helmet = getItem("iron_helmet")
            equipment.chestplate = null
            equipment.leggings = null
            equipment.boots = null
            equipment.helmetDropChance = 0.0f
            equipment.chestplateDropChance = 0.0f
            equipment.leggingsDropChance = 0.0f
            equipment.bootsDropChance = 0.0f
        }
    }
}