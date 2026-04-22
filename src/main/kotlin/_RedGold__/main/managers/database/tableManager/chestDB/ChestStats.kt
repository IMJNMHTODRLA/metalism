package _RedGold__.main.managers.database.tableManager.chestDB

import org.jetbrains.exposed.sql.Table

object ChestStats : Table("chest_stats") {
    val uuid = varchar("uuid", 36)
    val slot = integer("slot")
    val item = blob("item").nullable()

    override val primaryKey = PrimaryKey(uuid, slot)
}