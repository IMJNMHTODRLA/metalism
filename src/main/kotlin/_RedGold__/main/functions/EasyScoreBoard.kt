package _RedGold__.main.functions

import org.bukkit.scoreboard.Objective

object EasyScoreBoard {
    class ObjectiveProxy(private val board: Objective) {
        operator fun set(num: Int, text: String) {
            board.getScore(text).score = num
        }
    }

    val Objective.score get() = ObjectiveProxy(this)
}