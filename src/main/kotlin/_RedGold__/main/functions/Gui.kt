package _RedGold__.main.functions

import _RedGold__.main.functions.Color.gc
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Sound
import org.bukkit.attribute.Attribute
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*
import java.util.stream.Collectors

object Gui {
    fun getPlayerSkull(playerUuid: UUID, title: String? = null, description: List<String>? = null, t: Int? = null): ItemStack {
        val skull = ItemStack(Material.PLAYER_HEAD)
        val skullMeta = skull.itemMeta as SkullMeta

        val offlinePlayer = Bukkit.getOfflinePlayer(playerUuid)
        skullMeta.setOwningPlayer(offlinePlayer) // 스킨 적용
        if (title != null) skullMeta.setDisplayName(gc(title))

        if (description != null) {
            skullMeta.lore = description.stream()
                .map {line: String -> gc(line)}
                .collect(Collectors.toList())
        }

        skull.setItemMeta(skullMeta)
        if (t != null) skull.amount = t

        return skull
    }

    fun getItem(itemId: Material, title: String? = null, description: List<String>? = null, t: Int? = null): ItemStack {
        val item = ItemStack(itemId)
        val meta = item.itemMeta
        if (title != null) meta.setDisplayName(gc(title))

        if (description != null) {
            meta.lore = description.stream()
                .map {line: String -> gc(line)}
                .collect(Collectors.toList())
        }

        item.setItemMeta(meta)
        if (t != null) item.amount = t

        return item
    }

    fun itemDamage(item: ItemMeta, damage: Int): ItemMeta {
        val meta = item as? Damageable?: return item
        meta.damage = damage
        return meta
    }

    fun addItemDamage(item: ItemMeta, damage: Int): ItemMeta {
        val meta = item as? Damageable?: return item
        meta.damage += damage
        return meta
    }

    fun itemPotion(item: ItemMeta, potionType: PotionEffectType, time: Int, level: Int, over: Boolean = false): ItemMeta {
        val meta = item as? PotionMeta?: return item
        meta.addCustomEffect(PotionEffect(
            potionType,
            time * 20,
            level
        ), over)
        return meta
    }

    fun Player.addPotion(potionType: PotionEffectType, time: Int, level: Int = 0) {
        this.addPotionEffect(genPotion(potionType, time, level))
    }

    fun genPotion(potionType: PotionEffectType, time: Int, level: Int = 0): PotionEffect {
        return PotionEffect(potionType, time * 20, level)
    }

    //fun ItemStack.getStringId(key: NamespacedKey): String? {
    //    return this.itemMeta?.persistentDataContainer?.get(key, PersistentDataType.STRING)
    //} //TODO: 이동

    /* fun Player.addHeart(amount: Double) {
        val maxHealth = this.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value?: 20.0
        val currentHealth = this.health

        if (currentHealth + amount <= maxHealth) this.health += amount
        else {
            this.health = maxHealth
            this.absorptionAmount += (currentHealth + amount) - maxHealth
        }
    }*/

    fun LivingEntity.addHealth(amount: Double) {
        val maxHealth = this.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value?: 20.0
        val currentHealth = this.health

        if (currentHealth + amount <= maxHealth) this.health += amount
        else {
            this.health = maxHealth
            this.absorptionAmount += (currentHealth + amount) - maxHealth
        }
    }

    var LivingEntity.baseMaxHealth
        get() = this.properties[Attribute.GENERIC_MAX_HEALTH]
        set(value) {
            this.properties[Attribute.GENERIC_MAX_HEALTH] = value
        }

    fun Player.sendSound(sound: Sound, pitch: Float = 1f, volume: Float = 1f) {
        this.playSound(this.location, sound, volume, pitch)
    }

    class AttributeProxy(private val entity: LivingEntity) {
        operator fun get(type: Attribute): Double {
            return entity.getAttribute(type)?.baseValue?: 0.0
        }

        operator fun set(type: Attribute, value: Double) {
            entity.getAttribute(type)?.baseValue = value
        }
    }

    val LivingEntity.properties get() = AttributeProxy(this)

    @JvmInline
    value class InventoryAdder(val player: Player) {
        operator fun plusAssign(item: ItemStack) {
            player.inventory.addItem(item)
        }

        operator fun minusAssign(item: ItemStack) {
            player.inventory.removeItem(item)
        }

        operator fun plus(inventory: Inventory) {
            player.openInventory(inventory)
        }

        operator fun set(slot: EquipmentSlot, item: ItemStack) {
            player.inventory.setItem(slot, item)
        }

        fun safeAddItem(item: Material, times: Int) {
            if (times <= 0) return

            val maxStack = item.maxStackSize
            var remaining = times

            while (remaining > 0) {
                val amount = minOf(remaining, maxStack)

                val leftover = player.inventory.addItem(ItemStack(item, amount))

                if (leftover.isNotEmpty()) {
                    leftover.values.forEach {
                        player.world.dropItemNaturally(player.location, it)
                    }
                }
                remaining -= amount
            }
        }

        fun safeRemoveItem(item: Material, times: Int) {
            if (times <= 0) return

            val contents = player.inventory.storageContents
            var remaining = times

            for (i in contents.indices) {
                val stack = contents[i]?: continue
                if (stack.type != item) continue

                if (stack.amount <= remaining) {
                    remaining -= stack.amount
                    player.inventory.setItem(i, null)
                } else {
                    stack.amount -= remaining
                    player.inventory.setItem(i, stack)
                    remaining = 0
                }

                if (remaining <= 0) break
            }
        }

        /** false: 아이템 부족 | true: 아이템 넉넉*/
        fun hasAtLeast(type: Material, required: Int): Boolean {
            if (required <= 0) return true

            val contents = player.inventory.storageContents
            var count = 0

            for (i in contents.indices) {
                val element = contents[i]
                if (element != null && element.type == type) {
                    count += element.amount
                    if (count >= required) return true
                }
            }

            return false
        }

        /** false: 아이템 부족 | true: 아이템 넉넉*/
        fun hasAtLeast(type: ItemStack, required: Int): Boolean {
            if (required <= 0) return true

            val contents = player.inventory.storageContents
            var count = 0

            for (i in contents.indices) {
                val element = contents[i]
                if (element != null && element == type) {
                    count += element.amount
                    if (count >= required) return true
                }
            }

            return false
        }

        /** false: 공간 부족 | true: 공간 넉넉*/
        fun hasSpace(itemId: Material, amount: Int): Boolean {
            val contents = player.inventory.storageContents
            val maxStack = itemId.maxStackSize
            var remaining = amount

            for (slot in contents) {
                if (slot == null || slot.type.isAir) {
                    remaining -= maxStack
                }
                else if (slot.type == itemId) {
                    remaining -= (maxStack - slot.amount)
                }

                if (remaining <= 0) return true
            }
            return false
        }

        /** false: 공간 부족 | true: 공간 넉넉*/
        fun hasSpace(itemId: Material): Boolean {
            val contents = player.inventory.storageContents
            val maxStack = itemId.maxStackSize
            var remaining = 1

            for (slot in contents) {
                if (slot == null || slot.type.isAir) {
                    remaining -= maxStack
                }
                else if (slot.type == itemId) {
                    remaining -= (maxStack - slot.amount)
                }

                if (remaining <= 0) return true
            }
            return false
        }
    }

    val Player.inv get() = InventoryAdder(this)
}