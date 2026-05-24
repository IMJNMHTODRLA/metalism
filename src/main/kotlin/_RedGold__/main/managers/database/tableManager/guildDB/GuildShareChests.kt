package _RedGold__.main.managers.database.tableManager.guildDB

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object GuildShareChests : Table("guild_share_chests") {
    val guildId = integer("guild_id").references(GuildStats.id, ReferenceOption.CASCADE)
    val slot = integer("slot")
    val item = blob("item").nullable()

    override val primaryKey = PrimaryKey(guildId, slot)
}