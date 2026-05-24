package _RedGold__.main.core.guild

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.managers.database.tableManager.guildDB.GuildMembers
import _RedGold__.main.managers.guildDB
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SortOrder
import java.util.*

fun totalGuildMembers(id: Int) =
    transaction(guildDB) {
        GuildMembers
            .select(GuildMembers.guildId)
            .where { GuildMembers.guildId eq id }
            .count()
    }

fun getGuildId2Member(member: UUID) =
    transaction(guildDB) {
        val id = GuildMembers
            .select(GuildMembers.guildId)
            .where { GuildMembers.uuid eq member }
            .orderBy(GuildMembers.uuid to SortOrder.ASC)
            .limit(1)
            .map { it[GuildMembers.guildId] }
            .singleOrNull()

        joinedGuildCache[member] = (id?: return@transaction null)
        id
    }

fun isGuildJoin(target: UUID) =
    transaction(guildDB) {
        val id = GuildMembers
            .select(GuildMembers.guildId)
            .where { GuildMembers.uuid eq target }
            .orderBy(GuildMembers.uuid to SortOrder.ASC)
            .limit(1)
            .map { it[GuildMembers.guildId] }
            .singleOrNull()

        joinedGuildCache[target] = (id?: return@transaction null)
        id
    } != null

fun sendNotGuildJoinMsg(player: Player) = player.sendMsg("&c당신을 길드에 가입되어 있지 않습니다.")
/** @return true는 길드 가입, false는 길드 가입X */ fun isGuildJoin(player: Player): Boolean {
    val result = isGuildJoin(player.uniqueId)
    if (!result) sendNotGuildJoinMsg(player)

    return result
}