package _RedGold__.main.managers.database.tableManager.guildDB.guildBan

import _RedGold__.main.managers.database.tableManager.guildDB.GuildStats
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object GuildBans : Table("guild_bans") {
    val guildId = integer("guild_id").references(GuildStats.id, ReferenceOption.CASCADE)
    val uuid = uuid("uuid")
    val reason = enumerationByName<GuildBanType>("reason", 64)
    val bannedAt = long("banned_at")

    override val primaryKey = PrimaryKey(guildId, uuid)
}