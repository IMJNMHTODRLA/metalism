package _RedGold__.main.command.event.sys.rankRewardGui

import _RedGold__.main.Main.Event.EVENT_ITEM
import _RedGold__.main.Main.Event.EVENT_NAME
import _RedGold__.main.command.event.sys.eventGui.EventHolder
import _RedGold__.main.command.event.sys.rankRewardGui.RankRefresh.EventRank.eventTier
import _RedGold__.main.command.event.sys.rankRewardGui.RankRefresh.EventRank.isLoading
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.boostRank
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.waitUpdate
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.point
import _RedGold__.main.function.Color.fail
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.Gui.getPlayerSkull
import _RedGold__.main.function.api.toFormat
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import java.util.*

class RankRewardGui {
    fun getTier2Player(uuid: UUID, onlyColor: Boolean = false): String {
        val index = eventTier.indexOfFirst {it.key == uuid}
        if (index == -1) return if (onlyColor) "&8&l" else "&8&l랭크 없음"

        val rank = index + 1
        val totalPlayers = eventTier.size
        val topPercent = (rank.toDouble() / totalPlayers) * 100

        if (onlyColor) return when {
            rank == 1 -> "${rgb("D593FF")}&l"
            rank in 2..5 -> "${rgb("4FD0FF")}&l"
            topPercent <= 10.0 -> "${rgb("FFBFF4")}&l"
            topPercent <= 30.0 -> "${rgb("FFBF00")}&l"
            topPercent <= 50.0 -> "${rgb("999999")}&l"
            else -> "${rgb("895422")}&l"
        }
        return when {
            rank == 1 -> "${rgb("D593FF")}&l챌린저(1위)"
            rank in 2..5 -> "${rgb("4FD0FF")}&l마스터(2위~5위)"
            topPercent <= 10.0 -> "${rgb("FFBFF4")}&l플래티넘(6위~상위 10%)"
            topPercent <= 30.0 -> "${rgb("FFBF00")}&l골드(상위 11%~30%)"
            topPercent <= 50.0 -> "${rgb("999999")}&l실버(상위 31%~50%)"
            else -> "${rgb("895422")}&l브론즈(상위 51%~100%)"
        }
    }

    fun openGui(player: Player, page: Int) {
        val gui = EventHolder().inventory
        val uuid = player.uniqueId

        val now = System.currentTimeMillis() / 1000
        val checkingFuck = waitUpdate - now

        val minute = checkingFuck / 60
        val second = checkingFuck % 60

        if (isLoading) {
            player.fail("&c&l새로고침 중입니다. 잠시 기다려주세요.")
            return
        }

        val background = getItem(
            "light_blue_stained_glass_pane",
            """
                ${rgb("2444FC")}§l§o[
                ${rgb("2B49FC")}§l§oM
                ${rgb("324DFC")}§l§oE
                ${rgb("3952FD")}§l§oT
                ${rgb("4057FD")}§l§oA
                ${rgb("475CFD")}§l§oL
                ${rgb("4E60FD")}§l§oI
                ${rgb("5565FD")}§l§oS
                ${rgb("5B6AFE")}§l§oM 
                ${rgb("6973FE")}§l§oE
                ${rgb("7078FE")}§l§oV
                ${rgb("777DFE")}§l§oE
                ${rgb("7E82FF")}§l§oN
                ${rgb("8586FF")}§l§oT
                ${rgb("8C8BFF")}§l§o]
            """.trimIndent().replace("\n", "")
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        val itemLocation = listOf(
            1, 10, 19, 28,
            3, 12, 21, 30,
            5, 14, 23, 32,
            7, 16, 25, 34
        )

        for (i in 0 until 16) {
            val ranking = (page * 16) + i

            if (ranking >= eventTier.size) continue

            val targetPlayer = eventTier[ranking]
            val targetInfo = Bukkit.getOfflinePlayer(targetPlayer.key)
            val name = targetInfo.name?: "steve"
            val displayName = targetInfo.name?: "알 수 없음"
            val tier = getTier2Player(targetPlayer.key)
            val color = getTier2Player(targetPlayer.key, true)

            gui.setItem(itemLocation[i], getPlayerSkull(
                name,
                "&f&l플레이어: $color$displayName",
                listOf("", "&f&l랭크: $tier", "&f&l누적 이벤트 점수: &d&l${targetPlayer.value}")
            ).apply {addUnsafeEnchantment(Enchantment.LUCK_OF_THE_SEA, 1)})
        }

        gui.setItem(45, getItem(
            "red_stained_glass_pane",
            "&c이전 페이지로 이동(${page - 1})"
        ))

        gui.setItem(48, getPlayerSkull(
            player.name,
            "&b&l${player.name}&f&l님",
            listOf(
                "",
                "&f&l이벤트 점수: &d&l${point[player.uniqueId]?.toFormat()?: "-"} 점수",
                "&f&l랭크: ${getTier2Player(uuid)}",
                "",
                "&e&l클릭 시 랭킹 보상을 획득 할 수 있습니다.",
                "&e&l랭크 보상은 이벤트 종료 후 2일 뒤에 획득 가능합니다.",
                "&8&l새로고침까지 ${minute}분 ${second}초",
            )
        ).apply {addUnsafeEnchantment(Enchantment.SHARPNESS, 8)})

        gui.setItem(49, getItem(
            "book",
            "&8&l현재 페이지: ($page)",
        ))

        gui.setItem(50, getItem(
            "written_book",
            "&6&l랭킹 보상",
            listOf(
                "",
                "${rgb("D593FF")}&l챌린저(1위)&f&l:",
                "&6&l3,000,000 골드, &b&l200 캐시, &a&l경험치 병 64개,",
                "&d&l엔드 수정 64개, 리스폰 정박기 64개, 불사의 토템 24개",
                "",
                "${rgb("4FD0FF")}&l마스터(2위~5위)&f&l:",
                "&6&l2,250,000 골드, &b&l150 캐시, &a&l경험치 병 48개,",
                "&d&l엔드 수정 48개, 리스폰 정박기 48개, 불사의 토템 16개",
                "",
                "${rgb("FFBFF4")}&l플래티넘(6위~상위 10%)&f&l:",
                "&6&l2,000,000 골드, &b&l100 캐시, &a&l경험치 병 32개,",
                "&d&l엔드 수정 32개, 리스폰 정박기 32개, 불사의 토템 8개",
                "",
                "${rgb("FFBF00")}&l골드(상위 11%~30%)&f&l:",
                "&6&l1,500,000 골드, &b&l75 캐시, &a&l경험치 병 24개,",
                "&d&l엔드 수정 24개, 리스폰 정박기 24개",
                "",
                "${rgb("999999")}&l실버(상위 31%~50%)&f&l:",
                "&6&l1,250,000 골드, &b&l50 캐시, &a&l경험치 병 16개,",
                "&d&l엔드 수정 16개, 흑요석 24개",
                "",
                "${rgb("895422")}&l브론즈(상위 51%~100%)&f&l:",
                "&6&l1,000,000 골드, &b&l25 캐시, &a&l경험치 병 8개,",
                "&d&l엔드 수정 8개, 흑요석 16개",
                "",
            )
        ))

        gui.setItem(53, getItem(
            "green_stained_glass_pane",
            "&a다음 페이지로 이동(${page + 1})"
        ))

        player.openInventory(gui)
        player.playSound(player.location, Sound.AMBIENT_CAVE, 1f, 1f)
    }
}