package _RedGold__.main.commands.user.betting.listeners

import _RedGold__.main.functions.EasyHolder

abstract class BettingHolder : EasyHolder(6 * 9) {
    var isStart: Boolean = false
    var betGold: Long = 0
}
//TODO: 나중에 해보자