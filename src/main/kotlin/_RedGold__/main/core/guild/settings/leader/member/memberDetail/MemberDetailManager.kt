package _RedGold__.main.core.guild.settings.leader.member.memberDetail

import _RedGold__.main.core.guild.joinedGuildCache
import _RedGold__.main.core.guild.playerCooldownMsg
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.managers.database.tableManager.guildDB.GuildMembers
import _RedGold__.main.managers.database.tableManager.guildDB.GuildStats
import _RedGold__.main.managers.database.tableManager.guildDB.guildBan.GuildBanType
import _RedGold__.main.managers.database.tableManager.guildDB.guildBan.GuildBans
import _RedGold__.main.managers.guildDB
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.upsert
import java.util.UUID

fun startUtilityLogic(player: Player, target: OfflinePlayer, title: String, action: () -> Unit) {
    if (playerCooldownMsg(player)) return

    player.closeInventory()
    player.good("&a&l${target.name}님에게 ${title}&a&l(을)를 하였습니다.")

    taskAsync {
        action()
    }
}

fun transferLeader(id: Int, leader: UUID) =
    transaction(guildDB) {
        GuildStats
            .update({ GuildStats.id eq id }) {
                it[GuildStats.leader] = leader
            }
    }

private fun removeMemberLogic(id: Int, target: UUID) =
    GuildMembers.deleteWhere {
        (guildId eq id) and (uuid eq target)
    }

fun kickPlayer(id: Int, target: UUID) =
    transaction(guildDB) {
        joinedGuildCache.remove(target)
        removeMemberLogic(id, target)
    }

fun blockPlayer(id: Int, target: UUID) =
    transaction(guildDB) {
        joinedGuildCache.remove(target)
        removeMemberLogic(id, target)

        GuildBans.upsert {
            it[guildId] = id
            it[uuid] = target
            it[reason] = GuildBanType.BLOCK
            it[bannedAt] = now
        }
    }
