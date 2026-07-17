package _RedGold__.main.core.guild.homeManager

import _RedGold__.main.managers.database.tableManager.guildDB.GuildStats
import _RedGold__.main.managers.guildDB
import _RedGold__.main.managers.playerData.OVER_WORLD
import org.bukkit.Bukkit
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

fun getGuildHome(id: Int) =
    transaction(guildDB) {
        GuildStats
            .select(
                GuildStats.homeX,
                GuildStats.homeY,
                GuildStats.homeZ,
            )
            .where { GuildStats.id eq id }
            .limit(1)
            .map {
                val x = it[GuildStats.homeX]?: return@map null
                val y = it[GuildStats.homeY]?: return@map null
                val z = it[GuildStats.homeZ]?: return@map null

                Location(
                    Bukkit.getWorld(OVER_WORLD),
                    x, y, z
                )
            }
            .singleOrNull()
    }
