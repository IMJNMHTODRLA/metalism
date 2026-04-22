package _RedGold__.main.commands.user.ranking.listeners.killStreakGui

import _RedGold__.main.commands.user.ranking.listeners.GlobalValue
import _RedGold__.main.functions.FastBoolean.trueRun
import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.Gui.getPlayerSkull
import _RedGold__.main.functions.NumberFormat.toFormat
import org.bukkit.inventory.ItemStack
import java.util.*

object KillStreakConst {
    val getFormatRanking = { n: Int ->
        when(n) {
            0 -> Triple("&d&l", "1위!", true)
            1 -> Triple("&c&l", "2위", true)
            2 -> Triple("&4&l", "3위", true)
            in 3..100 -> Triple("&f&l", "${n + 1}위", false)
            else -> Triple("&7&l", "${n + 1}위", false)
        }
    }

    fun getRankingIcon(
        key: UUID, name: String,
        color: String, spot: String,
        value: Int, isEnchant: Boolean
    ): ItemStack {
        return getPlayerSkull(
            key,
            "&f&l플레이어: &e&l$name",
            listOf("", "&f&l순위: $color$spot", "&f&l연킬 수: $color${value.toFormat()}")
        ).apply {
            isEnchant.trueRun { enchantEffect() }
        }
    }

    fun dailyReward(uuid: UUID): Pair<Long, Int>? {
        val killStreakRank = GlobalValue.killStreakRank

        if (killStreakRank.size == 0) return null
        val index = killStreakRank.keys.indexOf(uuid)

        if (index == -1) return null

        val totalPlayers = killStreakRank.size
        val rank = index + 1
        val percentage = (rank.toFloat() / totalPlayers) * 100f

        return when {
            rank == 1 -> 172_800L to 45
            rank == 2 -> 158_400L to 40
            rank == 3 -> 144_000L to 35
            percentage <= 5.0f -> 129_600L to 30
            percentage <= 10.0f -> 115_200L to 25
            percentage <= 20.0f -> 100_800L to 20
            percentage <= 30.0f -> 86_400L to 18
            percentage <= 40.0f -> 72_000L to 16
            percentage <= 50.0f -> 57_600L to 14
            else -> 43_200L to 12
        }
    }
}