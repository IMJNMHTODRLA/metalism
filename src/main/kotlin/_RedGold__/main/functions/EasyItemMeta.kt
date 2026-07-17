package _RedGold__.main.functions

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

inline fun ItemStack.modifyMeta(action: ItemMeta.() -> Unit): ItemStack {
    val meta = itemMeta?: return this
    meta.action()
    itemMeta = meta
    return this
}

@JvmName("modifyMetaTyped")
inline fun <reified T : ItemMeta> ItemStack.modifyMeta(action: T.() -> Unit): ItemStack {
    val meta = itemMeta as? T?: return this
    meta.action()
    itemMeta = meta
    return this
}

inline fun ItemStack.modify(action: ItemStack.() -> Unit): ItemStack {
    action()
    return this
}

inline val ItemStack.plainDisplayName get() = itemMeta.displayName