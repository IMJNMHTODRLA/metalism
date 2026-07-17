package _RedGold__.main.commands.user.betting.listeners.coinGui

import _RedGold__.main.commands.user.betting.listeners.GlobalConst
import org.bukkit.Material

object CoinConst {
    const val MAX_BET = 15_000_000

    val GEN_RANDOM: Material get() {
        return when(GlobalConst.threadLocalRandom.nextBoolean()) {
            false -> Material.REDSTONE
            true -> Material.EMERALD //앞면
        }
    }

    val RANDOM_ITEM = { random: Boolean ->
        when(random) {
            false -> Material.REDSTONE to "&c&l뒷면"
            true -> Material.EMERALD  to "&a&l앞면"
        }
    }
}