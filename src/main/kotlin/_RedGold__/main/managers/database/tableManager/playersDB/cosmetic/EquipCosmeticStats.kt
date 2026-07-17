package _RedGold__.main.managers.database.tableManager.playersDB.cosmetic

import _RedGold__.main.managers.playerData.variableManager.cosmeticManager.CosmeticEnum
import org.jetbrains.exposed.sql.Table

object EquipCosmeticStats : Table("equip_cosmetic_stats") {
    val uuid = varchar("uuid", 36)
    val equipType = enumerationByName("equip_type", 32, CosmeticEnum::class)
    val equipItem = integer("equip_item")

    override val primaryKey = PrimaryKey(uuid, equipType)
}
