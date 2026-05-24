package _RedGold__.main.core.guild.settings.leader.member.memberDetail

import _RedGold__.main.core.guild.settings.leader.member.MemberData
import _RedGold__.main.functions.EasyHolder
import org.bukkit.OfflinePlayer

class MemberDetailHolder(val target: OfflinePlayer, val data: MemberData) : EasyHolder(54) {
    override fun title() = "길드원 상세정보"
}