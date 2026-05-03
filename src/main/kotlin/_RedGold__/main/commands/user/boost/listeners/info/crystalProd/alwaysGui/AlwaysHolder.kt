package _RedGold__.main.commands.user.boost.listeners.info.crystalProd.alwaysGui

import _RedGold__.main.functions.EasyHolder

class AlwaysHolder(var addCrystal: Int = 0) : EasyHolder(3 * 9) {
    override fun title() = "후원 정보(상시 판매 크리스탈)"

    val getTotalPrice get() = AlwaysConst.DEFAULT_PRICE * addCrystal
}