package _RedGold__.main.managers.database.tableManager.playersDB

import _RedGold__.main.managers.playerData.variableManager.MissionEnum
import org.jetbrains.exposed.sql.Table

object MissionStats : Table("mission_stats") {
    val uuid = varchar("uuid", 36)
    val type = enumerationByName("type", 64, MissionEnum::class)
    val index = integer("index")
    val progress = integer("progress")
    val isClaim = bool("is_claim")

    override val primaryKey = PrimaryKey(uuid, type, index)
}
