package _RedGold__.main.listeners.playerScoreboard

import org.bukkit.scoreboard.Scoreboard
import java.util.*

internal object PlayerScoreboardValue {
    val boards = mutableMapOf<UUID, Scoreboard>()
    var times = 0
}