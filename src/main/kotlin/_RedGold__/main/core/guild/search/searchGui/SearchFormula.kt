package _RedGold__.main.core.guild.search.searchGui

const val MAX_N = 28

fun getStartN(page: Int) = page * MAX_N

fun getIdFromN(page: Int, n: Int) = n + getStartN(page)
fun getSlot(n: Int) = n + 10 + (n / 7 * 2)
fun getIdFromSlot(page: Int, slot: Int) =
    slot.takeIf { it in 10..43 && it % 9 in 1..7 }
        ?.let { (it - 10) - ((it - 10) / 9 * 2) + getStartN(page) }
        ?: -1
