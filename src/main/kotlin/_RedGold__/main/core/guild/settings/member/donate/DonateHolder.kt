package _RedGold__.main.core.guild.settings.member.donate

import _RedGold__.main.functions.EasyHolder

class DonateHolder(val id: Int, var gold: Long = 0) : EasyHolder(36) {
    val upExp get() = gold / DEFAULT_GOLD * DEFAULT_GOLD2EXP
    override fun title() = "길드 기부"
}