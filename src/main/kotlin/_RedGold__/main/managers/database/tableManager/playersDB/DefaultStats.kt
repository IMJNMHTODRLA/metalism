package _RedGold__.main.managers.database.tableManager.playersDB

import org.jetbrains.exposed.sql.Table

object DefaultStats : Table("default_stats") {
    val uuid = varchar("uuid", 36)
    val gold = long("gold").index()
    val crystal = integer("crystal")
    val ruby = integer("ruby")
    val boost = integer("boost").index()

    override val primaryKey = PrimaryKey(uuid)
}