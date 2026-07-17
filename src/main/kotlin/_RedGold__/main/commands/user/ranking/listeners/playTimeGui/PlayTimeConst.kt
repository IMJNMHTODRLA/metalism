package _RedGold__.main.commands.user.ranking.listeners.playTimeGui

import _RedGold__.main.functions.FastBoolean.trueRun
import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.Gui.getPlayerSkull
import _RedGold__.main.functions.NumberFormat.toFormat
import org.bukkit.inventory.ItemStack
import java.util.*

object PlayTimeConst {
    val getFormatRanking = { n: Int ->
        when(n) {
            0 -> Triple("&b&l", "1위!", true)
            1 -> Triple("&2&l", "2위", true)
            2 -> Triple("&a&l", "3위", true)
            in 3..100 -> Triple("&f&l", "${n + 1}위", false)
            else -> Triple("&7&l", "${n + 1}위", false)
        }
    }

    fun getRankingIcon(
        key: UUID, name: String,
        color: String, spot: String,
        value: Float, isEnchant: Boolean
    ): ItemStack {
        return getPlayerSkull(
            key,
            "&f&l플레이어: &e&l$name",
            listOf("", "&f&l순위: $color$spot", "&f&l누적 플레이 타임: $color${value.toFormat(1)}")
        ).apply {
            isEnchant.trueRun { enchantEffect() }
        }
    }
}