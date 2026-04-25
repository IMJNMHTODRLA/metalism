package _RedGold__.main.managers.rankingManager

import java.util.*

data class RankingData(
    val uuid: UUID,

    val gold: Long = 0,
    val boost: Int = 0,

    val kill: Int = 0,
    val killStreak: Int = 0,
    val death: Int = 0,
    val deathStreak: Int = 0
)
