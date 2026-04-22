package _RedGold__.main.managers.chestManager

import _RedGold__.main.managers.chestDB
import _RedGold__.main.managers.database.tableManager.chestDB.ChestStats
import org.bukkit.inventory.ItemStack
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.batchUpsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun getChest(uuid: UUID, start: Long): Array<out ItemStack?> {
    val itemArray = arrayOfNulls<ItemStack>(DEF_CHEST_SLOT)

    transaction(chestDB) {
        ChestStats.selectAll()
            .orderBy(ChestStats.slot to SortOrder.ASC)
            .where { ChestStats.uuid eq uuid.toString() }
            .limit(DEF_CHEST_SLOT).offset(start)
            .forEachIndexed { i, row ->
                val rawItem = row[ChestStats.item]?.bytes
                if (i < itemArray.size) {
                    itemArray[i] = rawItem?.let { ItemStack.deserializeBytes(it) }
                }
            }
    }

    return itemArray
}

fun saveChest(uuid: UUID, start: Int, data: Array<out ItemStack?>) {
    isChestSaving.add(uuid)
    try {
        transaction(chestDB) {
            ChestStats.batchUpsert(
                data.withIndex()
            ) { (i, entry) ->
                this[ChestStats.uuid] = uuid.toString()
                this[ChestStats.slot] = start + i
                this[ChestStats.item] = entry?.serializeAsBytes()?.let(::ExposedBlob)
            }
        }
    } finally { isChestSaving.remove(uuid) }
}
