package _RedGold__.main.managers.database.tableManager.playersDB.cosmetic

import _RedGold__.main.managers.playerData.variableManager.cosmeticManager.CosmeticEnum
import org.jetbrains.exposed.sql.Table

object CosmeticStats : Table("cosmetic_stats") {
    val uuid = varchar("uuid", 36)
    val type = enumerationByName("type", 32, CosmeticEnum::class)
    val item = integer("item")

    override val primaryKey = PrimaryKey(uuid, type, item)
}
