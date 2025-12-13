package _RedGold__.main.command.shop.sys.goldShop.mineralGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.api.toFormat
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class MineralGui {
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

    private fun Inventory.fi(n: Int, id: String, title: String, sell: Int = 0, pur: Int = 0) {
        val nosell = sell == 0
        val nopur = pur == 0
        val gui = this

        gui.setItem(
            n, getItem(
                id,
                "&f&l$title",
                listOf(
                    "&f",
                    prefix,
                    if (nopur) "&c&l[구매 불가]" else "&a&l[구매(좌클릭)] &f&l구매가: ${pur.toFormat()}골드",
                    if (nopur) "" else "&8Shift + 좌클릭 시 64개가 구매됩니다.",
                    if (nosell) "&c&l[판매 불가]" else "&b&l[판매(우클릭)] &f&l판매가: ${sell.toFormat()}골드",
                    if (nosell) "" else "&8Shift + 우클릭 시 64개가 판매됩니다."
                )
            )
        )
    }

    fun openGui(player: Player) {
        val sellVal = listOf(
            900, 600, 700, 1000, 1100, 1000, 1100,
            600, 600, 2000, 900, 10000,
            100000, 900
        )

        val purVal = listOf(
            1100, 800, 900, 1300, 1400, 1300, 1400,
            800, 800, 3000, 1000, 12000,
            120000, 1000
        )

        val gui = MineralHolder(sellVal, purVal).inventory

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

        gui.fi(10, "coal", "석탄", sellVal[0], purVal[0]) //판매가, 구매가
        gui.fi(11, "raw_copper", "구리 원석", sellVal[1], purVal[1])
        gui.fi(12, "copper_ingot", "구리 주괴", sellVal[2], purVal[2])
        gui.fi(13, "raw_iron", "철 원석", sellVal[3], purVal[3])
        gui.fi(14, "iron_ingot", "철 주괴", sellVal[4], purVal[4])
        gui.fi(15, "raw_gold", "금 원석", sellVal[5], purVal[5])
        gui.fi(16, "gold_ingot", "금 주괴", sellVal[6], purVal[6])

        gui.fi(19, "lapis_lazuli", "청금석", sellVal[7], purVal[7])
        gui.fi(20, "redstone", "레드스톤", sellVal[8], purVal[8])
        gui.fi(21, "diamond", "다이아몬드", sellVal[9], purVal[9])
        gui.fi(22, "emerald", "에메랄드", sellVal[10], purVal[10])
        gui.fi(23, "netherite_scrap", "네더라이트 파편", sellVal[11], purVal[11])
        gui.fi(24, "netherite_ingot", "네더라이트 주괴", sellVal[12], purVal[12])
        gui.fi(25, "quartz", "석영", sellVal[13], purVal[13])

        player.openInventory(gui)
        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1f, 1f)
    }
}