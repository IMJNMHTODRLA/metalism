package _RedGold__.main.command.shop.sys.goldShop.foodGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class FoodGui {
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

    fun openGui(player: Player, page: Int) {
        val gui = FoodHolder(page).inventory

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
            "&8&l현재 페이지: ($page/2)",
        ))

        val purVal = listOf(
            800, 800, 800, 2500, 800, 1500, 1500,
            1500, 1500, 1500, 1500, 1500, 1500, 1500,
            2000, 1500, 1500, 1500, 1500
        )

        fun fi(n: Int, id: String, title: String, purId: Int = -1) {
            val pur = purVal.getOrNull(purId)?: 0
            val nopur = pur == 0

            gui.setItem(n, getItem(
                id,
                "&f&l$title",
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

        if (page == 1) {
            fi(10, "sweet_berries", "달콤한 열매", 0)
            fi(11, "glow_berries", "발광 열매", 1)
            fi(12, "chorus_fruit", "후렴과", 2)
            fi(13, "golden_carrot", "황금 당근", 3)
            fi(14, "baked_potato", "구운 감자", 4)
            fi(15, "bread", "빵", 5)
            fi(16, "cooked_beef", "스테이크", 6)

            fi(19, "cooked_porkchop", "익힌 돼지고기", 7)
            fi(20, "cooked_mutton", "익힌 양고기", 8)
            fi(21, "cooked_chicken", "익힌 닭고기", 9)
            fi(22, "cooked_rabbit", "익힌 토끼고기", 10)
            fi(23, "cooked_cod", "익힌 대구", 11)
            fi(24, "cooked_salmon", "익힌 연어", 12)
            fi(25, "cookie", "쿠키", 13)

            gui.setItem(35, getItem(
                "green_stained_glass_pane",
                "&a&l다음 페이지로 이동",
            ))
        } else {
            fi(10, "cake", "케이크", 14)
            fi(11, "pumpkin_pie", "호박 파이", 15)
            fi(12, "mushroom_stew", "버섯 스튜", 16)
            fi(13, "beetroot_soup", "비트 스튜", 17)
            fi(14, "rabbit_stew", "토끼 스튜", 18)
            fi(15, "barrier", "&c&l판매하고 있는 아이템이 아닙니다.")
            fi(16, "barrier", "&c&l판매하고 있는 아이템이 아닙니다.")

            fi(19, "barrier", "&c&l판매하고 있는 아이템이 아닙니다.")
            fi(20, "barrier", "&c&l판매하고 있는 아이템이 아닙니다.")
            fi(21, "barrier", "&c&l판매하고 있는 아이템이 아닙니다.")
            fi(22, "barrier", "&c&l판매하고 있는 아이템이 아닙니다.")
            fi(23, "barrier", "&c&l판매하고 있는 아이템이 아닙니다.")
            fi(24, "barrier", "&c&l판매하고 있는 아이템이 아닙니다.")
            fi(25, "barrier", "&c&l판매하고 있는 아이템이 아닙니다.")

            gui.setItem(27, getItem(
                "red_stained_glass_pane",
                "&c&l이전 페이지로 이동",
            ))
        }

        player.openInventory(gui)
        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1f, 1f)
    }
}