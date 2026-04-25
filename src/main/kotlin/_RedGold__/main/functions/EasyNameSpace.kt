package _RedGold__.main.functions

import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

fun ItemStack.getStringId(key: NamespacedKey) = getId(key, PersistentDataType.STRING)
fun <P, C> ItemStack.getId(key: NamespacedKey, type: PersistentDataType<P, C>): C? {
    return itemMeta?.persistentDataContainer?.get(key, type)
}

fun ItemMeta.getStringId(key: NamespacedKey) = getId(key, PersistentDataType.STRING)
fun <P, C> ItemMeta.getId(key: NamespacedKey, type: PersistentDataType<P, C>): C? {
    return persistentDataContainer.get(key, type)
}

fun ItemStack.setStringId(key: NamespacedKey, value: String) = setId(key, PersistentDataType.STRING, value)
fun <P, C> ItemStack.setId(key: NamespacedKey, type: PersistentDataType<P, C>, value: C & Any) {
    val meta = itemMeta?: return
    meta.persistentDataContainer.set(key, type, value)
    itemMeta = meta
}

fun ItemMeta.setStringId(key: NamespacedKey, value: String) = setId(key, PersistentDataType.STRING, value)
fun <P, C> ItemMeta.setId(key: NamespacedKey, type: PersistentDataType<P, C>, value: C & Any) = persistentDataContainer.set(key, type, value)
