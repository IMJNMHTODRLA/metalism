package _RedGold__.main.command.ranking.sys.playTimeGui

import _RedGold__.main.command.ranking.sys.Refresh.RankValue.isLoading
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.playTimeRank
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.Gui.getPlayerSkull
import _RedGold__.main.function.api.toFormat
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player

class PlayTimeGui {
    private val prefix = """
        ${rgb("2444FC")}§l[
        ${rgb("2947FC")}§lM
        ${rgb("2D4AFC")}§lE
        ${rgb("324DFC")}§lT
        ${rgb("3650FD")}§lA
        ${rgb("3B53FD")}§lL
        ${rgb("3F57FD")}§lI
        ${rgb("445AFD")}§lS
        ${rgb("485DFD")}§lM 
        ${rgb("5163FD")}§lP
        ${rgb("5666FD")}§lL
        ${rgb("5A69FE")}§lA
        ${rgb("5F6CFE")}§lY
        ${rgb("636FFE")}§lT
        ${rgb("6872FE")}§lI
        ${rgb("6C75FE")}§lM
        ${rgb("7178FE")}§lE 
        ${rgb("7078FE")}§lR
        ${rgb("747BFE")}§lA
        ${rgb("787DFE")}§lN
        ${rgb("7C80FF")}§lK
        ${rgb("8083FF")}§lI
        ${rgb("8486FF")}§lN
        ${rgb("8888FF")}§lG
        ${rgb("8C8BFF")}§l]
    """.trimIndent().replace("\n", "")

    private val itemLocation = listOf(
        1, 10, 19, 28,
        3, 12, 21, 30,
        5, 14, 23, 32,
        7, 16, 25, 34
    )

    fun openGui(player: Player, page: Int) {
        if (isLoading) {
            player.sendMessage("&c현재 순위를 새로고침하고 있습니다.")
            return
        }

        val gui = PlayTimeHolder(page).inventory

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

        for (i in 0 until 16) {
            val ranking = (page * 16) + i

            if (ranking >= playTimeRank.size) continue

            val targetPlayer = playTimeRank[ranking]
            val targetInfo = Bukkit.getOfflinePlayer(targetPlayer.key)
            val displayName = targetInfo.name?: "알 수 없음"
            val formatValue = targetPlayer.value.toFormat(3)
            val displayRank = ranking + 1

            val color = when(ranking) {
                0 -> "&2"
                1 -> rgb("00CE00")
                2 -> "&a"
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
                    "&f&l플레이 타임: ${color}&l${formatValue}일"
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

        gui.setItem(53, getItem(
            "green_stained_glass_pane",
            "&a다음 페이지로 이동(${page + 1})"
        ))

        player.openInventory(gui)
        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
    }
}