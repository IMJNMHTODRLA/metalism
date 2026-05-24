package _RedGold__.main.core.guild.settings.leader.whitelist.whitelistAdd

import _RedGold__.main.core.guild.settings.leader.whitelist.WhitelistData
import _RedGold__.main.functions.EasyHolder
import org.bukkit.OfflinePlayer

class WhitelistAddHolder(val target: OfflinePlayer, val data: WhitelistData) : EasyHolder(27) {
    override fun title() = "화이트 리스트 추가"
}