package _RedGold__.main.command.shop.sys.goldShop.cpvpGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class CpvpGui {
    val prefix = """
        ${rgb("2444FC")}§l§o[
        ${rgb("2948FC")}§l§oM
        ${rgb("2F4BFC")}§l§oE
        ${rgb("344FFC")}§l§oT
        ${rgb("3A53FD")}§l§oA
        ${rgb("3F57FD")}§l§oL
        ${rgb("455AFD")}§l§oI
        ${rgb("4A5EFD")}§l§oS
        ${rgb("5062FD")}§l§oM 
        ${rgb("5B69FE")}§l§oG
        ${rgb("606DFE")}§l§oO
        ${rgb("6671FE")}§l§oL
        ${rgb("6B75FE")}§l§oD 
        ${rgb("767CFE")}§l§oS
        ${rgb("7C80FF")}§l§oH
        ${rgb("8184FF")}§l§oO
        ${rgb("8787FF")}§l§oP
        ${rgb("8C8BFF")}§l§o]
    """.trimIndent().replace("\n", "")

    fun openGui(player: Player) {
        val gui = CpvpHolder().inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            prefix
        )

        val background2 = getItem(
            "black_stained_glass_pane",
            prefix
        )

        for (i in 0 until gui.size) gui.setItem(i, background)
        for (i in 27 until gui.size) gui.setItem(i, background2)

        gui.setItem(31, getItem(
            "book",
            "&8&l현재 페이지: (1/1)",
        ))

        val purVal = listOf(4000, 2000, 6000, 3000, 2500, 3500, 1500, 2500, 1500)

        fun fi(n: Int, id: String, title: String, valId: Int = -1) {
            val pur = purVal.getOrNull(valId)?: 0
            val nopur = pur == 0

            gui.setItem(n, getItem(
                id,
                "&5&l$title",
                listOf(
                    "&f",
                    prefix,
                    if (nopur) "&c&l[구매 불가]" else "&a&l[구매(좌클릭)] &f&l구매가: ${pur}골드",
                    if (nopur) "" else "&8Shift + 좌클릭 시 64개가 구매됩니다.",
                    "&c&l[판매 불가]",
                    ""
                )
            ))
        }

        fi(10, "end_crystal", "엔드 수정", 0)
        fi(11, "obsidian", "흑요석", 1)
        fi(12, "totem_of_undying", "불사의 토템", 2)
        fi(13, "golden_apple", "황금 사과", 3)
        fi(14, "ender_pearl", "엔더 진주", 4)
        fi(15, "respawn_anchor", "리스폰 정박기", 5)
        fi(16, "glowstone", "발광석", 6)

        fi(19, "experience_bottle", "경험치 병", 7)
        fi(20, "arrow", "화살", 8)
        fi(21, "barrier", "&c&l판매하고 있는 아이템이 아닙니다.")
        fi(22, "barrier", "&c&l판매하고 있는 아이템이 아닙니다.")
        fi(23, "barrier", "&c&l판매하고 있는 아이템이 아닙니다.")
        fi(24, "barrier", "&c&l판매하고 있는 아이템이 아닙니다.")
        fi(25, "barrier", "&c&l판매하고 있는 아이템이 아닙니다.")

        player.openInventory(gui)
        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1f, 1f)
    }
}