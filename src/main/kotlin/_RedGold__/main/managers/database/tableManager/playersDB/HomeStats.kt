package _RedGold__.main.managers.database.tableManager.playersDB

import org.jetbrains.exposed.sql.Table

object HomeStats : Table("home_stats") {
    val uuid = varchar("uuid", 36)
    val index = integer("index")
    val isUnlocked = bool("is_unlocked")

    val world = text("world")
    val x = double("x")
    val y = double("y")
    val z = double("z")
    val yaw = float("yaw")
    val pitch = float("pitch")

    override val primaryKey = PrimaryKey(uuid, index)
}