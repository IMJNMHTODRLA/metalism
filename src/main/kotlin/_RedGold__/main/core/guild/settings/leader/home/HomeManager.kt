package _RedGold__.main.core.guild.settings.leader.home

import _RedGold__.main.managers.database.tableManager.guildDB.GuildStats
import _RedGold__.main.managers.guildDB
import org.bukkit.Location
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

fun setGuildHome(id: Int, location: Location) =
    transaction(guildDB) {
        GuildStats
            .update({ GuildStats.id eq id }) {
                it[homeX] = location.x
                it[homeY] = location.y
                it[homeZ] = location.z
            }
    }
