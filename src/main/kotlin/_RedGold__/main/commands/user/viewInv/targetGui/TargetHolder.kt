package _RedGold__.main.commands.user.viewInv.targetGui

import _RedGold__.main.functions.EasyHolder

class TargetHolder(private val name: String) : EasyHolder(6 * 9) {
    override fun title() = "${name}님의 인벤토리"
}