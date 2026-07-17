package _RedGold__.main.commands.user.betting.listeners.diceGui

import _RedGold__.main.functions.EasyHolder

class DiceHolder(
    var isStart: Boolean = false,
    var select: Int? = null,
    var betGold: Long = 0
) : EasyHolder(6 * 9) {
    override fun title() = "주사위 굴리기 도박(성공: x4.5, 실패: x0)"
}