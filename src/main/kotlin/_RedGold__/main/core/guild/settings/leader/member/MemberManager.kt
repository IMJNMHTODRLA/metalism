package _RedGold__.main.core.guild.settings.leader.member

import _RedGold__.main.managers.database.tableManager.guildDB.GuildMembers
import _RedGold__.main.managers.guildDB
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

data class MemberData(
    val id: Int,
    val players: List<UUID>
) {
    constructor(id: Int, vararg players: UUID) : this(id, players.toList())
}

fun getMemberData(id: Int, page: Int): MemberData {
    return transaction(guildDB) {
        val players = GuildMembers
            .select(GuildMembers.uuid)
            .where { GuildMembers.guildId eq id }
            .orderBy(GuildMembers.guildId to SortOrder.ASC)
            .limit(28)
            .offset(page * 28L)
            .map { it[GuildMembers.uuid] }

        MemberData(id, players)
    }
}
