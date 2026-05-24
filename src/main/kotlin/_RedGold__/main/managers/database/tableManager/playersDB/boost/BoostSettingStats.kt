package _RedGold__.main.managers.database.tableManager.playersDB.boost

import _RedGold__.main.managers.playerData.variableManager.boostSettingManager.BoostSettingEnum
import org.jetbrains.exposed.sql.Table

object BoostSettingStats : Table("boost_setting_stats") {
    val uuid = varchar("uuid", 36)
    val type = enumerationByName("type", 64, BoostSettingEnum::class)
    val value = integer("value")

    override val primaryKey = PrimaryKey(uuid, type)
}
