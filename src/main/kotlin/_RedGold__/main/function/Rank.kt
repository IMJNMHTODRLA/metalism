package _RedGold__.main.function

import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.util.*

object Rank {
    fun getPlayerRankPrefix(player: Player): String {
        var veryPrefix = ""

        if (player.hasPermission("Main.user")) veryPrefix = "&7&l[USER]&7"
        if (player.hasPermission("Main.plus")) veryPrefix = "&a&l[PLUS]&a"
        if (player.hasPermission("Main.admin")) veryPrefix = "&c&l[ADMIN]&c"
        if (player.hasPermission("Main.owner")) veryPrefix = "&c&l[OWNER]&c"

        return veryPrefix.replace("&", "§")
    }

    fun getPlayerRank(player: Player): String {
        var rank = ""
        if (player.hasPermission("Main.user")) rank = "USER"
        if (player.hasPermission("Main.plus")) rank = "PLUS"
        if (player.hasPermission("Main.admin")) rank = "ADMIN"
        if (player.hasPermission("Main.owner")) rank = "OWNER"

        return rank
    }

    fun hasRank(player: Player, rank: String): Boolean {
        var rank = rank
        rank = rank.lowercase(Locale.getDefault())

        return player.hasPermission("Main.${rank}")
    }

    fun getRankPrefix(rank: String): String {
        return when(rank.lowercase(Locale.getDefault())) {
            "user" -> "&7&l[USER]&7"
            "plus" -> "&a&l[PLUS]&a"
            "admin" -> "&c&l[ADMIN]&c"
            "owner" -> "&c&l[OWNER]&c"
            else -> ""
        }.replace("&", "§")
    }
}