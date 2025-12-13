package _RedGold__.main.command.shop.sys.goldShop.enchantGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.api.toFormat
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class EnchantGui {
    private val prefix = """
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

    private fun Inventory.fi(n: Int, title: String, pur: Int) {
        val gui = this

        gui.setItem(n, getItem(
            "enchanted_book",
            "&b&l$title",
            listOf(
                "&f",
                prefix,
                "&a&l[구매(좌클릭)] &f&l구매가: ${pur.toLong().toFormat()}골드",
                "&8Shift + 좌클릭 시 64개가 구매됩니다.",
                "&c&l[판매 불가]"
            )
        ))
    }

    private fun Inventory.none(n: Int) {
        val gui = this

        gui.setItem(n, getItem(
            "barrier",
            "&c&l판매하고 있는 아이템이 아닙니다.",
            listOf(
                "&f",
                prefix,
                "&c&l[구매 불가]",
                "",
                "&c&l[판매 불가]",
                ""
            )
        ))
    }

    fun openGui(player: Player, page: Int) {
        val background = getItem(
            "magenta_stained_glass_pane",
            prefix
        )

        val background2 = getItem(
            "black_stained_glass_pane",
            prefix
        )

        val gui = EnchantHolder(page).inventory

        for (i in 0 until gui.size) gui.setItem(i, background)
        for (i in 27 until gui.size) gui.setItem(i, background2)

        gui.setItem(27, getItem(
            "red_stained_glass_pane",
            "&c&l이전 페이지로 이동",
        ))

        gui.setItem(31, getItem(
            "book",
            "&8&l현재 페이지: ($page/3)",
        ))

        gui.setItem(35, getItem(
            "green_stained_glass_pane",
            "&a&l다음 페이지로 이동",
        ))

        val buyData = listOf(
            450000, 450000, 550000, 450000, 550000, 450000, 550000, 500000, 550000, 600000,
            500000, 550000, 550000, 500000, 550000, 550000, 450000, 550000, 500000, 450000,
            450000, 700000, 450000, 550000, 550000, 500000, 650000, 450000, 500000, 550000,
            500000, 650000, 550000, 500000, 500000, 500000, 550000, 500000, 550000, 500000
        )

        when (page) {
            1 -> {
                gui.fi(10, "친수성", buyData[0]) //구매
                gui.fi(11, "살충 V", buyData[1])
                gui.fi(12, "폭발로부터 보호 IV", buyData[2])
                gui.fi(13, "격파 IV", buyData[3])
                gui.fi(14, "집전", buyData[4])
                gui.fi(15, "육중 V", buyData[5])
                gui.fi(16, "물갈퀴 III", buyData[6])

                gui.fi(19, "효율 V", buyData[7])
                gui.fi(20, "가벼운 착지 IV", buyData[8])
                gui.fi(21, "발화 II", buyData[9])
                gui.fi(22, "화염으로부터 보호 IV", buyData[10])
                gui.fi(23, "화염", buyData[11])
                gui.fi(24, "행운 III", buyData[12])
                gui.fi(25, "차가운 걸음 II", buyData[13])

                gui.setItem(27, background2)
            }
            2 -> {
                gui.fi(10, "찌르기 V", buyData[14]) //판매, 구매
                gui.fi(11, "무한", buyData[15])
                gui.fi(12, "밀치기 II", buyData[16])
                gui.fi(13, "약탈 III", buyData[17])
                gui.fi(14, "충성 III", buyData[18])
                gui.fi(15, "바다의 행운 III", buyData[19])
                gui.fi(16, "미끼 III", buyData[20])

                gui.fi(19, "수선", buyData[21])
                gui.fi(20, "다중 발사", buyData[22])
                gui.fi(21, "관통 IV", buyData[23])
                gui.fi(22, "힘 V", buyData[24])
                gui.fi(23, "발사체로부터 보호 IV", buyData[25])
                gui.fi(24, "보호 IV", buyData[26])
                gui.fi(25, "밀어내기 II", buyData[27])
            }
            else -> {
                gui.fi(10, "빠른 장전 III", buyData[28]) //판매, 구매
                gui.fi(11, "호흡 III", buyData[29])
                gui.fi(12, "급류 III", buyData[30])
                gui.fi(13, "날카로움 V", buyData[31])
                gui.fi(14, "섬세한 손길", buyData[32])
                gui.fi(15, "강타 V", buyData[33])
                gui.fi(16, "영혼 가속 III", buyData[34])

                gui.fi(19, "휩쓸기 III", buyData[35])
                gui.fi(20, "신속한 잠행 III", buyData[36])
                gui.fi(21, "가시 III", buyData[37])
                gui.fi(22, "내구성 III", buyData[38])
                gui.fi(23, "돌풍 III", buyData[39])
                gui.none(24)
                gui.none(25)

                gui.setItem(35, background2)
            }
        }

        player.openInventory(gui)
        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1f, 1f)
    }
}