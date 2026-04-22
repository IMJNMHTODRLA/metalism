package _RedGold__.main.managers.database.tableManager.playersDB.cosmetic

import _RedGold__.main.managers.database.tableManager.playersDB.Users
import _RedGold__.main.managers.playerData.variableManager.cosmeticManager.CosmeticEnum
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object EquipCosmeticStats : Table("equip_cosmetic_stats") {
    val userId = varchar("user_id", 8).references(Users.id, onDelete = ReferenceOption.CASCADE)
    val equipType = enumerationByName("equip_type", 32, CosmeticEnum::class)
    val equipItem = integer("equip_item")

    override val primaryKey = PrimaryKey(userId, equipType)
}
