package _RedGold__.main.core.guild.settings.leader.member

fun getSlot(n: Int) = n + 10 + (n / 7 * 2)
fun getId(slot: Int) =
    slot.takeIf { it in 10..43 && it % 9 in 1..7 }
        ?.let { (it - 10) - ((it - 10) / 9 * 2) }
