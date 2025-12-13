package _RedGold__.main.command.ranking.sys.deathGui

import _RedGold__.main.command.ranking.sys.Refresh
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.deathRank
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.isLoading
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.Gui.getPlayerSkull
import _RedGold__.main.function.api.toFormat
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player

class DeathGui {
    fun openGui(player: Player, page: Int) {
        if (isLoading) {
            player.sendMessage("&c현재 순위를 새로고침하고 있습니다.")
            return
        }

        val gui = DeathHolder(page).inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            """
                ${rgb("2444FC")}§l§o[
                ${rgb("2947FC")}§l§oM
                ${rgb("2D4AFC")}§l§oE
                ${rgb("324DFC")}§l§oT
                ${rgb("3650FD")}§l§oA
                ${rgb("3B53FD")}§l§oL
                ${rgb("3F57FD")}§l§oI
                ${rgb("445AFD")}§l§oS
                ${rgb("485DFD")}§l§oM 
                ${rgb("5163FD")}§l§oD
                ${rgb("5666FD")}§l§oE
                ${rgb("5A69FE")}§l§oA
                ${rgb("5F6CFE")}§l§oT
                ${rgb("636FFE")}§l§oH 
                ${rgb("6C75FE")}§l§oR
                ${rgb("7178FE")}§l§oA
                ${rgb("757CFE")}§l§oN
                ${rgb("7A7FFE")}§l§oK
                ${rgb("7E82FF")}§l§oI
                ${rgb("8385FF")}§l§oN
                ${rgb("8788FF")}§l§oG
                ${rgb("8C8BFF")}§l§o]
            """.trimIndent().replace("\n", "")
        )

        val background2 = getItem(
            "black_stained_glass_pane",
            """
                ${rgb("2444FC")}§l§o[
                ${rgb("2947FC")}§l§oM
                ${rgb("2D4AFC")}§l§oE
                ${rgb("324DFC")}§l§oT
                ${rgb("3650FD")}§l§oA
                ${rgb("3B53FD")}§l§oL
                ${rgb("3F57FD")}§l§oI
                ${rgb("445AFD")}§l§oS
                ${rgb("485DFD")}§l§oM 
                ${rgb("5163FD")}§l§oD
                ${rgb("5666FD")}§l§oE
                ${rgb("5A69FE")}§l§oA
                ${rgb("5F6CFE")}§l§oT
                ${rgb("636FFE")}§l§oH 
                ${rgb("6C75FE")}§l§oR
                ${rgb("7178FE")}§l§oA
                ${rgb("757CFE")}§l§oN
                ${rgb("7A7FFE")}§l§oK
                ${rgb("7E82FF")}§l§oI
                ${rgb("8385FF")}§l§oN
                ${rgb("8788FF")}§l§oG
                ${rgb("8C8BFF")}§l§o]
            """.trimIndent().replace("\n", "")
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

            if (ranking >= deathRank.size) continue

            val targetPlayer = deathRank[ranking]
            val targetInfo = Bukkit.getOfflinePlayer(targetPlayer.key)
            val name = targetInfo.name?: "steve"
            val displayName = targetInfo.name?: "알 수 없음"

            when (ranking) {
                0 -> {
                    gui.setItem(itemLocation[i], getPlayerSkull(
                        name,
                        "&f&l플레이어: &e&l$displayName",
                        listOf("", "&f&l순위: &4&l1위!", "&f&l사망 수: &4&l${targetPlayer.value.toFormat()}")
                    ).apply {addUnsafeEnchantment(Enchantment.LUCK_OF_THE_SEA, 1)})
                }
                1 -> {
                    gui.setItem(itemLocation[i], getPlayerSkull(
                        name,
                        "&f&l플레이어: &e&l$displayName",
                        listOf("", "&f&l순위: &c&l2위", "&f&l사망 수: &c&l${targetPlayer.value.toFormat()}")
                    ).apply {addUnsafeEnchantment(Enchantment.LUCK_OF_THE_SEA, 2)})
                }
                2 -> {
                    gui.setItem(itemLocation[i], getPlayerSkull(
                        name,
                        "&f&l플레이어: &e&l$displayName",
                        listOf("", "&f&l순위: &e&l3위", "&f&l사망 수: &e&l${targetPlayer.value.toFormat()}")
                    ).apply {addUnsafeEnchantment(Enchantment.LUCK_OF_THE_SEA, 3)})
                }
                in 3..100 -> {
                    gui.setItem(itemLocation[i], getPlayerSkull(
                        name,
                        "&f&l플레이어: &e&l$displayName",
                        listOf("", "&f&l순위: &f&l${ranking + 1}위", "&f&l사망 수: &f&l${targetPlayer.value.toFormat()}")
                    ))
                }
                else -> {
                    gui.setItem(itemLocation[i], getPlayerSkull(
                        name,
                        "&f&l플레이어: &e&l$displayName",
                        listOf("", "&f&l순위: &7&l${ranking + 1}위", "&f&l사망 수: &7&l${targetPlayer.value.toFormat()}")
                    ))
                }
            }
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