package _RedGold__.main.core.guild.search.searchGui

import _RedGold__.main.core.guild.search.GuildInfoData
import _RedGold__.main.functions.EasyHolder

class SearchHolder(
    val page: Int,
    val keyword: String?,
    val infoList: List<GuildInfoData>
) : EasyHolder(54) {
    override fun title() = "길드(검색|$page|$keyword)"
}