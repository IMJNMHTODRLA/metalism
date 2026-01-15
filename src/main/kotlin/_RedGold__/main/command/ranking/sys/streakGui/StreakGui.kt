package _RedGold__.main.command.ranking.sys.streakGui

import _RedGold__.main.command.ranking.sys.Refresh.RankValue.KILL_STREAK_MIN_GOLD
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.KILL_STREAK_MIN_SUBS
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.KILL_STREAK_MIN_TIMES
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.isLoading
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.killStreakRank
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.killStreakReward
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.Gui.getPlayerSkull
import _RedGold__.main.function.api.toFormat
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import java.util.*

class StreakGui {
    private val prefix = """
        ${rgb("2444FC")}§l[
        ${rgb("2847FC")}§lM
        ${rgb("2D4AFC")}§lE
        ${rgb("314DFC")}§lT
        ${rgb("3550FD")}§lA
        ${rgb("3A53FD")}§lL
        ${rgb("3E56FD")}§lI
        ${rgb("4259FD")}§lS
        ${rgb("475CFD")}§lM 
        ${rgb("4F62FD")}§lS
        ${rgb("5465FD")}§lT
        ${rgb("5868FE")}§lR
        ${rgb("5C6AFE")}§lE
        ${rgb("616DFE")}§lA
        ${rgb("6570FE")}§lK 
        ${rgb("6E76FE")}§lR
        ${rgb("7279FE")}§lA
        ${rgb("767CFE")}§lN
        ${rgb("7B7FFF")}§lK
        ${rgb("7F82FF")}§lI
        ${rgb("8385FF")}§lN
        ${rgb("8888FF")}§lG
        ${rgb("8C8BFF")}§l]
    """.trimIndent().replace("\n", "")

    private fun getDefaultReward2Player(uuid: UUID): Pair<Float, Float> {
        val index = killStreakRank.indexOfFirst {it.key == uuid}
        if (index == -1 || killStreakRank.isEmpty()) return Pair(0.0f, 0.0f)

        val totalPlayers = killStreakRank.size
        val totalPrizePool = totalPlayers * (totalPlayers / KILL_STREAK_MIN_SUBS).coerceAtLeast(KILL_STREAK_MIN_TIMES)

        val rank = index + 1
        val percentage = (rank.toFloat() / totalPlayers) * 100f

        val halving = if (totalPlayers < 5) 2f
            else if (totalPlayers < 10) 1.5f
            else 1f

        val pizza = when {
            rank == 1 -> totalPrizePool * 0.31f
            rank in 2..5 -> totalPrizePool * 0.23f
            percentage <= 10.0f -> totalPrizePool * 0.19f
            percentage <= 30.0f -> totalPrizePool * 0.14f
            percentage <= 50.0f -> totalPrizePool * 0.09f
            else -> totalPrizePool * 0.04f
        }.coerceAtLeast(KILL_STREAK_MIN_GOLD) / halving

        val giveExp = when {
            rank == 1 -> 3.12f
            rank in 2..5 -> 2.62f
            percentage <= 10.0f -> 2.21f
            percentage <= 30.0f -> 1.63f
            percentage <= 50.0f -> 1.21f
            else -> 0.85f
        } / halving

        return pizza to giveExp
    }

    fun openGui(player: Player, page: Int) {
        if (isLoading) {
            player.sendMessage("&c현재 순위를 새로고침하고 있습니다.")
            return
        }

        val gui = StreakHolder(page).inventory
        val uuid = player.uniqueId

        val background = getItem(
            "magenta_stained_glass_pane",
            prefix
        )

        val background2 = getItem(
            "black_stained_glass_pane",
            prefix
        )

        for (i in 0 until gui.size) gui.setItem(i, background)
        for (i in 45 until gui.size) gui.setItem(i, background2)

        val itemLocation = listOf(
            1, 10, 19, 28,
            3, 12, 21, 30,
            5, 14, 23, 32,
            7, 16, 25, 34
        )

        for (i in 0 until 16) {
            val ranking = (page * 16) + i

            if (ranking >= killStreakRank.size) continue

            val targetPlayer = killStreakRank[ranking]
            val targetInfo = Bukkit.getOfflinePlayer(targetPlayer.key)
            val displayName = targetInfo.name?: "알 수 없음"
            val formatValue = targetPlayer.value.toFormat()
            val displayRank = ranking + 1

            val color = when(ranking) {
                0 -> "&c"
                1 -> rgb("D83636")
                2 -> "&4"
                in 3..100 -> "&f"
                else -> "&7"
            }
            val isWow = if (displayRank == 1) "!" else ""

            gui.setItem(itemLocation[i], getPlayerSkull(
                targetPlayer.key,
                "&f&l플레이어: &e&l$displayName",
                listOf(
                    "",
                    "&f&l순위: ${color}&l${displayRank}위$isWow",
                    "&f&l총 연킬: ${color}&l${formatValue}킬&8&l(매 일마다 연킬은 초기화 됩니다.)"
                )
            ).apply {addUnsafeEnchantment(Enchantment.LUCK_OF_THE_SEA, 1)})
        }

        gui.setItem(45, getItem(
            "red_stained_glass_pane",
            "&c이전 페이지로 이동(${page - 1})"
        ))

        gui.setItem(49, getItem(
            "book",
            "&8&l현재 페이지: ($page)",
        ))

        val (defaultGold, defaultExp) = getDefaultReward2Player(uuid)

        val reward = killStreakReward[uuid]
        val gold = reward?.gold?: 0f
        val exp = reward?.exp?: 0f

        gui.setItem(50, getItem(
            "gold_ingot",
            "&6&l보상 획득하기",
            listOf(
                "",
                "&f&l골드: &6&l${gold.toFormat(2)} 골드&8&l(+${defaultGold.toFormat(2)})",
                "&f&l경험치: &a&l${exp.toFormat(2)} 경험치&8&l(+${defaultExp.toFormat(2)})",
                "",
                "&7&l매 분마다 보상이 누적되며 보상 획득 시 소수점 이하는 버려집니다.",
                "&7&l서버가 재시작 시 미수령 보상은 초기화 됩니다.",
                "",
                "&e&l클릭하여 보상 획득하기",
            )
        ))

        gui.setItem(53, getItem(
            "green_stained_glass_pane",
            "&a다음 페이지로 이동(${page + 1})"
        ))

        player.openInventory(gui)
        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
    }
}