package _RedGold__.main.managers.chestManager

import _RedGold__.main.managers.chestDB
import _RedGold__.main.managers.database.tableManager.chestDB.ChestStats
import org.bukkit.inventory.ItemStack
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.less
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchUpsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun getChest(uuid: UUID, start: Int): Array<out ItemStack?> {
    val itemArray = arrayOfNulls<ItemStack>(DEF_CHEST_SLOT)

    transaction(chestDB) {
        val end = start + DEF_CHEST_SLOT

        ChestStats.selectAll()
            .where {
                (ChestStats.uuid eq uuid.toString()) and
                (ChestStats.slot greaterEq start) and
                (ChestStats.slot less end)
            }
            .forEach { row ->
                val absoluteSlot = row[ChestStats.slot]
                val relativeSlot = absoluteSlot % DEF_CHEST_SLOT

                val rawItem = row[ChestStats.item]?.bytes
                if (relativeSlot in 0..<DEF_CHEST_SLOT) {
                    itemArray[relativeSlot] = rawItem?.let { ItemStack.deserializeBytes(it) }
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
