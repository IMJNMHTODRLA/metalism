package _RedGold__.main.core.guild.settings.member.leave

import _RedGold__.main.functions.EasyHolder

class LeaveHolder(var deleteTimes: Int = 3) : EasyHolder(27) {
    override fun title() = "길드 탈퇴"
}