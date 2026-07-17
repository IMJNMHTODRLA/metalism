package _RedGold__.main.commands.user.betting.listeners

import java.util.concurrent.ThreadLocalRandom

object GlobalConst {
    val threadLocalRandom: ThreadLocalRandom = ThreadLocalRandom.current()
    const val DEFAULT_GOLD = 1000
    const val BET_GOLD_MESSAGE = "&6&l배팅 금액&f: &6&l%gold% 골드"
}