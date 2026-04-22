package _RedGold__.main.functions

import org.bukkit.Bukkit
import org.bukkit.entity.Player

fun <T> getTabPlayers(s: String = "", selector: (Player) -> T) = Bukkit.getOnlinePlayers()
    .map { selector(it) }
    .filter { it.toString().startsWith(s, true) }
