package _RedGold__.main.managers.userShopManager

import _RedGold__.main.functions.NumberFormat.toUuid
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.managers.database.tableManager.userShopDB.UserShopStats
import _RedGold__.main.managers.userShopDB
import org.bukkit.inventory.ItemStack
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun getItemFromId(id: Int): UserShopDetailData? {
    return transaction(userShopDB) {
        UserShopStats.select(
            UserShopStats.id,
            UserShopStats.uuid,

            UserShopStats.priceType,
            UserShopStats.priceAmount,

            UserShopStats.item
        )
        .where { UserShopStats.id eq id }
        .map { row ->
            UserShopDetailData(
                id,
                row[UserShopStats.uuid].toUuid(),

                row[UserShopStats.priceType],
                row[UserShopStats.priceAmount],

                ItemStack.deserializeBytes(row[UserShopStats.item].bytes)
            )
        }
        .singleOrNull()
    }
}

fun getAllUserShopData(): List<UserShopInfoData> {
    return transaction(userShopDB) {
        UserShopStats.select(
            UserShopStats.id,
            UserShopStats.uuid,

            UserShopStats.createdAt,

            UserShopStats.displayMaterial,
            UserShopStats.displayName,
            UserShopStats.displayIsEnchant,
            UserShopStats.displayAmount
        )
        .orderBy(UserShopStats.id to SortOrder.ASC)
        .map { row ->
            UserShopInfoData(
                row[UserShopStats.id],
                row[UserShopStats.uuid].toUuid(),

                row[UserShopStats.createdAt],

                row[UserShopStats.displayMaterial],
                row[UserShopStats.displayName],
                row[UserShopStats.displayIsEnchant],
                row[UserShopStats.displayAmount],
            )
        }
    }
}

fun updateAllUserShopData(
    data: List<UserShopInfoData>, newData: MutableMap<UUID, MutableList<UserShopEntry>>
) {
    transaction(userShopDB) {
        val targetIds = data.filter {
            it.isPurchase ||
            it.isDelete
        }.map { it.id }

        if (targetIds.isNotEmpty()) UserShopStats.deleteWhere { id inList targetIds }

        val values = newData.values.flatten()
        if (values.isEmpty()) return@transaction

        val maxId = UserShopStats
            .select(UserShopStats.id)
            .orderBy(UserShopStats.id to SortOrder.DESC)
            .limit(1)
            .map { it[UserShopStats.id] }
            .singleOrNull() ?: 0

        // 3. 변수를 하나 만들어서 데이터를 넣을 때마다 1씩 증가시킵니다.
        var nextId = maxId + 1

        UserShopStats.batchInsert(values) { entry ->
            val info = entry.info
            val detail = entry.detail

            this[UserShopStats.id] = nextId++

            this[UserShopStats.uuid] = info.uuid.toString()

            this[UserShopStats.priceType] = detail.priceType
            this[UserShopStats.priceAmount] = detail.priceAmount

            this[UserShopStats.createdAt] = now

            this[UserShopStats.item] = ExposedBlob(detail.item.serializeAsBytes())

            this[UserShopStats.displayMaterial] = info.displayMaterial
            this[UserShopStats.displayName] = info.displayName
            this[UserShopStats.displayIsEnchant] = info.displayIsEnchant
            this[UserShopStats.displayAmount] = info.displayAmount
        }
    }
}
