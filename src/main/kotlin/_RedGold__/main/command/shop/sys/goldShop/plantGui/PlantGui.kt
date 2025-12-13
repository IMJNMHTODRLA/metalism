package _RedGold__.main.command.shop.sys.goldShop.plantGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.defData
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Gui.getItem
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin

class PlantGui(private val plugin: JavaPlugin) {
    private fun Inventory.fi(n: Int, id: String, title: String, sell: Int = 0, pur: Int = 0, sellTimes: Int, sellMax: Int = 256) {
        val nosell = sell == 0
        val nopur = pur == 0
        val gui = this

        gui.setItem(
            n, getItem(
                id,
                "&f&l$title &8판매 횟수: ($sellTimes/$sellMax)",
                listOf(
                    "&f",
                    """
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
                    """.trimIndent().replace("\n", ""),
                    if (nopur) "&c&l[구매 불가]" else "&a&l[구매(좌클릭)] &f&l구매가: ${pur}골드",
                    if (nopur) "" else "&8Shift + 좌클릭 시 64개가 구매됩니다.",
                    if (nosell) "&c&l[판매 불가]" else "&b&l[판매(우클릭)] &f&l판매가: ${sell}골드",
                    if (nosell) "" else "&8Shift + 우클릭 시 64개가 판매됩니다."
                )
            )
        )
    }

    fun openGui(player: Player) {
        val sellTimes = mutableListOf<Int>()
        for (i in 0 until 14) sellTimes.add(getData(plugin, player, "plant_shop/$i").toInt())

        val gui = PlantHolder(sellTimes).inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            """
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
        )

        val background2 = getItem(
            "black_stained_glass_pane",
            """
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
        )

        for (i in 0 until gui.size) gui.setItem(i, background)
        for (i in 27 until gui.size) gui.setItem(i, background2)

        gui.setItem(31, getItem(
            "book",
            "&8&l현재 페이지: (1/1)",
        ))

        gui.fi(10, "wheat", "밀", 800, 0, sellTimes[0]) //판매가, 구매가, 현재 판매 횟수, 최대 판매 횟수
        gui.fi(11, "wheat_seeds", "밀 씨앗", 100, 150, sellTimes[1], 4096)
        gui.fi(12, "beetroot", "비트", 800, 0, sellTimes[2])
        gui.fi(13, "beetroot_seeds", "비트 씨앗", 100, 150, sellTimes[3], 4096)
        gui.fi(14, "potato", "감자", 600, 700, sellTimes[4])
        gui.fi(15, "poisonous_potato", "독이 든 감자", 6500, 0, sellTimes[5], 16)
        gui.fi(16, "carrot", "당근", 600, 700, sellTimes[6])

        gui.fi(19, "nether_wart", "네더 사마귀", 1200, 1500, sellTimes[7])
        gui.fi(20, "pumpkin", "호박", 700, 0, sellTimes[8])
        gui.fi(21, "pumpkin_seeds", "호박씨", 100, 150, sellTimes[9], 4096)
        gui.fi(22, "melon_slice", "수박 조각", 500, 0, sellTimes[10])
        gui.fi(23, "melon_seeds", "수박씨", 100, 150, sellTimes[11], 4096)
        gui.fi(24, "cocoa_beans", "코코아 콩", 500, 600, sellTimes[12])
        gui.fi(25, "sugar_cane", "사탕수수", 800, 900, sellTimes[13])

        player.openInventory(gui)
        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1f, 1f)
    }
}