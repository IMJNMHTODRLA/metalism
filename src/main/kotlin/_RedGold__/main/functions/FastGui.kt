package _RedGold__.main.functions

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object FastGui {
    @JvmInline
    value class FastGuiProxy(private val inventory: Inventory) {
        operator fun get(index: Int) = inventory.getItem(index)
        operator fun set(index: Int, item: ItemStack?) = inventory.setItem(index, item)

        operator fun set(range: IntRange, item: ItemStack?) {
            range.forEach { i -> inventory.setItem(i, item) }
        }

        operator fun invoke(item: ItemStack?) {
            inventory.contents.indices.forEach { inventory.setItem(it, item) }
        }
    }

    val Inventory.item get() = FastGuiProxy(this)
    fun ItemStack.enchantEffect() = modifyMeta {
        addItemFlags(ItemFlag.HIDE_ENCHANTS)
        addEnchant(Enchantment.LURE, 1, true)
    }

    val Inventory.end get() = this.size - 1
}