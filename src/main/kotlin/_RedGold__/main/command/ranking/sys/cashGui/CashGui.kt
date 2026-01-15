package _RedGold__.main.command.ranking.sys.cashGui

import _RedGold__.main.command.ranking.sys.Refresh.RankValue.cashRank
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.goldRank
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.isLoading
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.Gui.getPlayerSkull
import _RedGold__.main.function.api.toFormat
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player

class CashGui {
    private val prefix = """
        ${rgb("2444FC")}§l§o[
        ${rgb("2947FC")}§l§oM
        ${rgb("2D4AFC")}§l§oE
        ${rgb("324EFC")}§l§oT
        ${rgb("3751FD")}§l§oA
        ${rgb("3C54FD")}§l§oL
        ${rgb("4057FD")}§l§oI
        ${rgb("455BFD")}§l§oS
        ${rgb("4A5EFD")}§l§oM 
        ${rgb("5364FD")}§l§oC
        ${rgb("5868FE")}§l§oA
        ${rgb("5D6BFE")}§l§oS
        ${rgb("616EFE")}§l§oH 
        ${rgb("6B74FE")}§l§oR
        ${rgb("7078FE")}§l§oA
        ${rgb("747BFE")}§l§oN
        ${rgb("797EFE")}§l§oK
        ${rgb("7E81FF")}§l§oI
        ${rgb("8385FF")}§l§oN
        ${rgb("8788FF")}§l§oG
        ${rgb("8C8BFF")}§l§o]
    """.trimIndent().replace("\n", "")

    fun openGui(player: Player, page: Int) {
        if (isLoading) {
            player.sendMessage("&c현재 순위를 새로고침하고 있습니다.")
            return
        }

        val gui = CashHolder(page).inventory

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

            if (ranking >= cashRank.size) continue

            val targetPlayer = cashRank[ranking]
            val targetInfo = Bukkit.getOfflinePlayer(targetPlayer.key)
            val displayName = targetInfo.name?: "알 수 없음"
            val formatValue = targetPlayer.value.toFormat()
            val displayRank = ranking + 1

            val color = when(ranking) {
                0 -> "&b"
                1 -> rgb("15D8D8")
                2 -> "&3"
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
                    "&f&l보유 캐시: ${color}&l${formatValue}"
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