package _RedGold__.main.managers.rankingManager

import java.util.*

data class RankingData(
    val uuid: UUID,

    var gold: Long = 0,
    var boost: Int = 0,

    var kill: Int = 0,
    var killStreak: Int = 0,
    var death: Int = 0,
    var deathStreak: Int = 0
)
