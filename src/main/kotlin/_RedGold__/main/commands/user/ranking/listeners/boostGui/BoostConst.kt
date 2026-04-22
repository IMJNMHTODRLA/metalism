package _RedGold__.main.commands.user.ranking.listeners.boostGui

import _RedGold__.main.functions.FastBoolean.trueRun
import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.Gui.getPlayerSkull
import _RedGold__.main.functions.NumberFormat.toFormat
import org.bukkit.inventory.ItemStack
import java.util.*

internal object BoostConst {
    val getFormatRanking = { n: Int ->
        when(n) {
            0 -> Triple("&b&l", "1위!", true)
            1 -> Triple("&a&l", "2위", true)
            2 -> Triple("&2&l", "3위", true)
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
            listOf("", "&f&l순위: $color$spot", "&f&l누적 후원 금액: $color${value.toFormat()}")
        ).apply {
            isEnchant.trueRun { enchantEffect() }
        }
    }
}