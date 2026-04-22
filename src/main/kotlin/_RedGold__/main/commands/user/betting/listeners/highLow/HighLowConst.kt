package _RedGold__.main.commands.user.betting.listeners.highLow

import _RedGold__.main.commands.user.betting.listeners.GlobalConst
import org.bukkit.Material

internal object HighLowConst {
    const val MAX_BET = 5_000_000

    val GEN_RANDOM: Material get() {
        return when(GlobalConst.secureRandom.nextInt(100) + 1) {
            in 0..49 -> Material.REDSTONE
            in 51..100 -> Material.EMERALD //초과
            else -> Material.CHISELED_STONE_BRICKS
        }
    }

    val RANDOM_ITEM = { random: Int ->
        when(random) {
            in 0..49 -> Material.REDSTONE to "&c&l50 미만"
            in 51..100 -> Material.EMERALD  to "&a&l50 초과" //초과
            else -> Material.CHISELED_STONE_BRICKS  to "&7&l정확히 50"
        }
    }
}