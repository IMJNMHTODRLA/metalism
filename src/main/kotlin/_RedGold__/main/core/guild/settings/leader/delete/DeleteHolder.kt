package _RedGold__.main.core.guild.settings.leader.delete

import _RedGold__.main.functions.EasyHolder

class DeleteHolder(val id: Int, var deleteTimes: Int = 3) : EasyHolder(27) {
    override fun title() = "길드 삭제"
}