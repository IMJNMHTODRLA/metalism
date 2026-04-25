package _RedGold__.main.functions

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object EasyEnchant {
    class EnchantProxy(private val item: ItemStack) {
        operator fun contains(enchant: Enchantment?): Boolean {
            return item.containsEnchantment(
                enchant?: return item.itemMeta.hasEnchants()
            )
        }

        operator fun get(enchant: Enchantment): Int {
            return item.itemMeta.getEnchantLevel(enchant)
        }

        operator fun set(vararg enchants: Enchantment, value: Int): Boolean {
            return try {
                enchants.forEach { enchant ->
                    item.addUnsafeEnchantment(enchant, value)
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    val ItemStack.enchant: EnchantProxy get() = EnchantProxy(this)
}