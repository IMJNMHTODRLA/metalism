package _RedGold__.main.managers.database.tableManager.banDB

import _RedGold__.main.managers.banManager.BanEnum
import org.jetbrains.exposed.sql.Table

object BanStats : Table("ban_stats") {
    val uuid = varchar("uuid", 36)
    val type = enumerationByName("type", 64, BanEnum::class)
    val reason = text("reason")
    val expiresAt = long("expires_at").nullable()
    val bannedAt = long("banned_at")

    override val primaryKey = PrimaryKey(uuid)
}
