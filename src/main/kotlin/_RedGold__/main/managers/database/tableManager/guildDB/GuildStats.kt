package _RedGold__.main.managers.database.tableManager.guildDB

import org.jetbrains.exposed.sql.Table

object GuildStats : Table("guild_stats") {
    val id = integer("id").autoIncrement()

    val name = varchar("name", 16).uniqueIndex()
    val leader = uuid("leader").uniqueIndex()
    val exp = long("exp")

    val homeX = double("home_x").nullable()
    val homeY = double("home_y").nullable()
    val homeZ = double("home_z").nullable()

    /** 검색 노출 허용 여부 */ val allowSearch = bool("allow_search")
    /** 길드 채팅 허용 여부 */ val allowChat = bool("allow_chat")
    /** 화이트리스트 활성화 여부 */ val enableWhitelist = bool("enable_whitelist")
    /** 길드원과 PVP 가능 여부 */ val allowPvp = bool("allow_pvp")

    val createdAt = long("created_at")

    override val primaryKey = PrimaryKey(id)
}