package _RedGold__.main.functions

import org.bukkit.NamespacedKey
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

typealias NSK = NamespacedKey
typealias AutoPDT<P, C> = PersistentDataType<P, C>

//fun ItemStack.getStringId(key: NSK) = getId(key, AutoPDT.STRING)
//fun <P, C> ItemStack.getId(key: NSK, type: AutoPDT<P, C>) = itemMeta.getId(key, type)


//fun ItemStack.setStringId(key: NSK, value: String) = setId(key, AutoPDT.STRING, value)
//fun <P, C> ItemStack.setId(key: NSK, type: AutoPDT<P, C>, value: C & Any) = itemMeta.setId(key, type, value)

fun ItemMeta.getStringId(key: NSK) = getId(key, AutoPDT.STRING)
fun ItemMeta.getBooleanId(key: NSK) = getId(key, AutoPDT.BOOLEAN)
fun ItemMeta.getIntegerId(key: NSK) = getId(key, AutoPDT.INTEGER)
fun <P, C> ItemMeta.getId(key: NSK, type: AutoPDT<P, C>) = persistentDataContainer.get(key, type)

fun ItemMeta.setStringId(key: NSK, value: String) = setId(key, AutoPDT.STRING, value)
fun ItemMeta.setBooleanId(key: NSK, value: Boolean) = setId(key, AutoPDT.BOOLEAN, value)
fun ItemMeta.setIntegerId(key: NSK, value: Int) = setId(key, AutoPDT.INTEGER, value)

fun <P, C> ItemMeta.setId(key: NSK, type: AutoPDT<P, C>, value: C & Any) = persistentDataContainer.set(key, type, value)
