package _RedGold__.main.commands.user.ranking.listeners.killStreakGui

import _RedGold__.main.commands.user.ranking.listeners.GlobalConst
import _RedGold__.main.commands.user.ranking.listeners.GlobalValue
import _RedGold__.main.functions.FastGui.end
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.getPlayerSkull
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.BACKGROUND_1
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class KillStreakGui {
    fun openGui(player: Player, page: Int) {
        val gui = KillStreakHolder(page).inventory
        val uuid = player.uniqueId
        val killStreakRank = GlobalValue.killStreakRank

        gui.item[0..44] = BACKGROUND
        gui.item[45..gui.end] = BACKGROUND_1

        repeat(16) { i ->
            val ranking = GlobalConst.getRanking(page, i)
            val slot = GlobalConst.getSlot(i)

            if (ranking >= killStreakRank.size) return@repeat
            val (_, key, value) = killStreakRank[ranking]

            val offlinePlayer = Bukkit.getOfflinePlayer(key)
            val name = offlinePlayer.name?: "알 수 없음"

            val formatRankingInfo = KillStreakConst.getFormatRanking(ranking)
            gui.item[slot] = KillStreakConst.getRankingIcon(
                key, name,
                formatRankingInfo.first, formatRankingInfo.second,
                value, formatRankingInfo.third
            )
        }

        gui.item[45] = getItem(
            Material.RED_STAINED_GLASS_PANE,
            "&c이전 페이지로 이동(${page - 1})"
        )

        val playerRanking = killStreakRank[uuid]
        val playerRank = playerRanking?.let { it.rank + 1 }?.toFormat()?: "-"

        gui.item[48] = getPlayerSkull(
            uuid,
            "&8&l현재 페이지: ($page)",
            listOf("", "&f&l현재 순위: &e&l${playerRank}위", "&8&l클릭 시 현재 순위로 이동 됩니다.")
        )

        val (giveGold, giveCrystal) = KillStreakConst.dailyReward(uuid)?.run {
            first.toFormat() to second.toFormat()
        }?: ("-" to "-")

        gui.item[50] = getItem(
            Material.GOLD_INGOT,
            "&6&l일일 보상 획득하기",
            listOf(
                "",
                "&f&l골드: &6&l$giveGold 골드",
                "&f&l크리스탈: &b&l$giveCrystal 크리스탈",
                "",
                "&7&l오후 4시 이후부터 보상 획득이 가능합니다.",
                "&7&l서버가 재시작 시 미수령 보상은 초기화 됩니다.",
                "",
                "&e&l클릭하여 보상 획득하기",
            )
        )

        gui.item[53] = getItem(
            Material.GREEN_STAINED_GLASS_PANE,
            "&a다음 페이지로 이동(${page + 1})"
        )

        player.inv + gui
        player.sendSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
    }
}