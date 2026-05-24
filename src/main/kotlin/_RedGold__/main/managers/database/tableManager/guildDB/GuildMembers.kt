package _RedGold__.main.managers.database.tableManager.guildDB

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object GuildMembers : Table("guild_members") {
    val guildId = integer("guild_id").references(GuildStats.id, ReferenceOption.CASCADE)
    val uuid = uuid("uuid")
    val joinedAt = long("joined_at")

    override val primaryKey = PrimaryKey(uuid)
}