package _RedGold__.main.commands.user.ranking.listeners

internal object GlobalValue {
    @Volatile var nextUpdate = 0L

    @Volatile var goldRank = GlobalConst.LongRankingData() //완
    @Volatile var boostRank = GlobalConst.IntRankingData() //완
    @Volatile var playTimeRank = GlobalConst.FloatRankingData() //완

    @Volatile var killRank = GlobalConst.IntRankingData() //완
    @Volatile var killStreakRank = GlobalConst.IntRankingData() //완

    @Volatile var deathRank = GlobalConst.IntRankingData() //완
    @Volatile var deathStreakRank = GlobalConst.IntRankingData() //완
}