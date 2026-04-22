package _RedGold__.main.commands.user.ranking.listeners.deathGui

import _RedGold__.main.commands.user.ranking.listeners.GlobalConst
import _RedGold__.main.commands.user.ranking.listeners.GlobalValue
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.getPlayerSkull
import _RedGold__.main.functions.FastGui.end
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.BACKGROUND_1
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class DeathGui {
    fun openGui(player: Player, page: Int) {
        val gui = DeathHolder(page).inventory
        val uuid = player.uniqueId
        val deathRank = GlobalValue.deathRank

        gui.item[0..44] = BACKGROUND
        gui.item[45..gui.end] = BACKGROUND_1

        repeat(16) { i ->
            val ranking = GlobalConst.getRanking(page, i)
            val slot = GlobalConst.getSlot(i)

            if (ranking >= deathRank.size) return@repeat
            val (_, key, value) = deathRank[ranking]

            val offlinePlayer = Bukkit.getOfflinePlayer(key)
            val name = offlinePlayer.name?: "알 수 없음"

            val formatRankingInfo = DeathConst.getFormatRanking(ranking)
            gui.item[slot] = DeathConst.getRankingIcon(
                key, name,
                formatRankingInfo.first, formatRankingInfo.second,
                value, formatRankingInfo.third
            )
        }

        gui.item[45] = getItem(
            Material.RED_STAINED_GLASS_PANE,
            "&c이전 페이지로 이동(${page - 1})"
        )

        val playerRanking = deathRank[uuid]
        val playerRank = playerRanking?.let { it.rank + 1 }?.toFormat()?: "-"

        gui.item[49] = getPlayerSkull(
            uuid,
            "&8&l현재 페이지: ($page)",
            listOf("", "&f&l현재 순위: &e&l${playerRank}위", "&8&l클릭 시 현재 순위로 이동 됩니다.")
        )

        gui.item[53] = getItem(
            Material.GREEN_STAINED_GLASS_PANE,
            "&a다음 페이지로 이동(${page + 1})"
        )

        player.inv + gui
        player.sendSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
    }
}