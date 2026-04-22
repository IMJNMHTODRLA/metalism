package _RedGold__.main.managers.database.tableManager.playersDB.boost

import _RedGold__.main.managers.playerData.variableManager.BoostEnum
import org.jetbrains.exposed.sql.Table

object BoostStats : Table("boost_stats") {
    val uuid = varchar("uuid", 36)
    val type = enumerationByName("type", 64, BoostEnum::class)
    val amount = integer("amount")
    val expirationAt = long("expiration_at")

    override val primaryKey = PrimaryKey(uuid, type)
}