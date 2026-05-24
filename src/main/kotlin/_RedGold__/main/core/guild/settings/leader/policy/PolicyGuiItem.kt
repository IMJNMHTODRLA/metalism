package _RedGold__.main.core.guild.settings.leader.policy

import _RedGold__.main.functions.Gui.getItem
import org.bukkit.Material

val policyDisplayItemList = arrayOf(
    PolicyData::allowSearch to getItem(Material.BOOK, "&e&l검색 노출 여부"),
    PolicyData::allowChat to getItem(Material.NAME_TAG, "&e&l길드 채팅 여부"),
    PolicyData::enableWhitelist to getItem(Material.MOJANG_BANNER_PATTERN, "&e&l화이트리스트 여부"),
    PolicyData::allowPvp to getItem(Material.PLAYER_HEAD, "&e&l길드원과 PVP 여부"),
    null to getItem(Material.BLACK_STAINED_GLASS_PANE, "&8&lCOMING SOON...")
)