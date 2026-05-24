package _RedGold__.main.commands.user.viewEc.targetGui

import _RedGold__.main.functions.EasyHolder

class TargetHolder(private val name: String) : EasyHolder(3 * 9) {
    override fun title() = "${name}님의 엔더 상자"
}