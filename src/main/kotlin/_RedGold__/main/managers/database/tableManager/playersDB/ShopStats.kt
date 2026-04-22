package _RedGold__.main.managers.database.tableManager.playersDB

import org.jetbrains.exposed.sql.Table

object ShopStats : Table("shop_stats") {
    val uuid = varchar("uuid", 36)
    val isPurchaseMonthly = bool("is_purchase_monthly")

    override val primaryKey = PrimaryKey(uuid)
}