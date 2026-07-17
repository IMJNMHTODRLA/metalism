package _RedGold__.main.managers.database.tableManager.playersDB

import org.jetbrains.exposed.sql.Table

object HomeStats : Table("home_stats") {
    val uuid = varchar("uuid", 36)
    val index = integer("index")
    val isUnlocked = bool("is_unlocked")

    val x = double("x").nullable()
    val y = double("y").nullable()
    val z = double("z").nullable()
    val yaw = float("yaw").nullable()
    val pitch = float("pitch").nullable()

    override val primaryKey = PrimaryKey(uuid, index)
}