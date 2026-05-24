package _RedGold__.main.core.guild.settings.leader.member

import _RedGold__.main.functions.EasyHolder
import org.bukkit.OfflinePlayer

class MemberHolder(val data: List<OfflinePlayer>, val page: Int) : EasyHolder(54) {
    override fun title() = "길드원 설정"
}