package _RedGold__.main.function

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*
import java.util.stream.Collectors

object Gui {
    fun getPlayerSkull(playerName: String, title: String, description: List<String>?): ItemStack {
        val skull = ItemStack(Material.PLAYER_HEAD)
        val skullMeta = skull.itemMeta as SkullMeta

        val offlinePlayer = Bukkit.getOfflinePlayer(playerName)
        skullMeta.setOwningPlayer(offlinePlayer) // 스킨 적용
        skullMeta.setDisplayName(title.replace("&", "§")) // 아이템 이름 설정

        if (description != null) {
            skullMeta.lore = description.stream()
                .map { line: String -> line.replace("&", "§") }
                .collect(Collectors.toList())
        }

        skull.setItemMeta(skullMeta)

        return skull
    }

    fun getItem(itemID: String, title: String? = null, description: List<String>? = null): ItemStack {
        val material = Material.valueOf(itemID.replace("minecraft:", "").uppercase(Locale.getDefault()))

        val item = ItemStack(material)
        val meta = item.itemMeta
        if (title != null) meta.setDisplayName(title.replace("&", "§"))

        if (description != null) {
            meta.lore = description.stream()
                .map { line: String -> line.replace("&", "§") }
                .collect(Collectors.toList())
        }

        item.setItemMeta(meta)

        return item
    }

    fun itemDamage(item: ItemMeta, damage: Int): ItemMeta {
        val meta = item as? Damageable?: return item
        meta.damage = damage
        return meta
    }

    fun itemPotion(item: ItemMeta, potionType: PotionEffectType, time: Int, level: Int, over: Boolean = false): ItemMeta {
        val meta = item as? PotionMeta ?: return item
        meta.addCustomEffect(PotionEffect(
            potionType,
            time * 20,
            level
        ), over)
        return meta
    }
}