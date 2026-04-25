package _RedGold__.main.functions

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

inline infix fun ItemStack.modifyMeta(action: ItemMeta.() -> Unit): ItemStack {
    val meta = this.itemMeta?: return this
    meta.action()
    this.itemMeta = meta
    return this
}

inline infix fun ItemStack.modify(action: ItemStack.() -> Unit): ItemStack {
    this.action()
    return this
}