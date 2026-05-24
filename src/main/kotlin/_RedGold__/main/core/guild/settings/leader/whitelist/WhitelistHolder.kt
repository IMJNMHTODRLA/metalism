package _RedGold__.main.core.guild.settings.leader.whitelist

import _RedGold__.main.functions.EasyHolder
import org.bukkit.OfflinePlayer

class WhitelistHolder(val data: List<OfflinePlayer>, val page: Int) : EasyHolder(54) {
    override fun title() = "화이트 리스트"
}