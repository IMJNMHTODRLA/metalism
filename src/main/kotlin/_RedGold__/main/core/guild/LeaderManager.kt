package _RedGold__.main.core.guild

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.managers.database.tableManager.guildDB.GuildStats
import _RedGold__.main.managers.guildDB
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun getGuildId2Leader(leader: UUID) =
    transaction(guildDB) {
        val id = GuildStats
            .select(GuildStats.id)
            .where { GuildStats.leader eq leader }
            .orderBy(GuildStats.leader to SortOrder.ASC)
            .limit(1)
            .map { it[GuildStats.id] }
            .singleOrNull()

        joinedGuildCache[leader] = (id?: return@transaction null)
        id
    }

fun isLeader(leader: UUID) =
    transaction(guildDB) {
        val id = GuildStats
            .select(GuildStats.id)
            .where { GuildStats.leader eq leader }
            .orderBy(GuildStats.leader to SortOrder.ASC)
            .limit(1)
            .map { it[GuildStats.id] }
            .singleOrNull()

        joinedGuildCache[leader] = (id?: return@transaction null)
        id
    } != null

fun sendNotLeaderMsg(player: Player) = player.sendMsg("&c당신을 리더가 아닙니다.")
/** @return true는 리더다, false는 리더 아니다 */ fun isLeader(player: Player): Boolean {
    val result = isLeader(player.uniqueId)
    if (!result) sendNotLeaderMsg(player)

    return result
}