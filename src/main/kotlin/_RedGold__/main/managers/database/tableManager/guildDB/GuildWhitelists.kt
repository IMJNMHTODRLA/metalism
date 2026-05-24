package _RedGold__.main.managers.database.tableManager.guildDB

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object GuildWhitelists : Table("guild_whitelists") {
    val guildId = integer("guild_id").references(GuildStats.id, ReferenceOption.CASCADE)
    val uuid = uuid("uuid")

    override val primaryKey = PrimaryKey(guildId, uuid)
}