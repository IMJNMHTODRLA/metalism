package _RedGold__.main.core.guild.shareChestManager

import _RedGold__.main.managers.database.tableManager.guildDB.GuildShareChests
import _RedGold__.main.managers.guildDB
import org.bukkit.inventory.ItemStack
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.batchUpsert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import org.jetbrains.exposed.sql.transactions.transaction

fun getShareChest(id: Int, size: Int) =
    transaction(guildDB) {
        val itemArray = arrayOfNulls<ItemStack>(size)

        GuildShareChests
            .selectAll()

            .orderBy(GuildShareChests.slot to SortOrder.ASC)
            .where { GuildShareChests.guildId eq id }

            .limit(size)

            .forEachIndexed { i, row ->
                val rawItem = row[GuildShareChests.item]?.bytes
                if (i < itemArray.size) {
                    itemArray[i] = rawItem?.let { ItemStack.deserializeBytes(it) }
                }
            }

        itemArray
    }

fun saveShareChest(id: Int, data: Array<ItemStack?>) =
    transaction(guildDB) {
        GuildShareChests.batchUpsert(
            data.withIndex()
        ) { (i, entry) ->
            this[GuildShareChests.guildId] = id
            this[GuildShareChests.slot] = i
            this[GuildShareChests.item] = entry?.serializeAsBytes()?.let(::ExposedBlob)
        }
    }