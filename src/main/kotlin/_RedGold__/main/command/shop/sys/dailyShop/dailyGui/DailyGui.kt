package _RedGold__.main.command.shop.sys.dailyShop.dailyGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.defData
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.load.RequireJavaPlugin
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin

class DailyGui(private val plugin: JavaPlugin) {
    private fun Inventory.fi(n: Int, id: String, title: String, pur: Int, buyTimes: Int, buyMax: Int) {
        this.setItem(n, getItem(
            id,
            "&f&l$title &8구매 횟수: ($buyTimes/$buyMax)",
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
                "&a&l[구매(좌클릭)] &f&l구매가: ${pur}원"
            ))
        )
    }

    fun openGui(player: Player) {
        val buyTimes = mutableListOf<Int>()
        for (i in 0..6) buyTimes.add(getData(plugin, player, "daily_shop/$i").toInt())

        val gui = DailyHolder(buyTimes).inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            """
                ${rgb("2444FC")}§l§o[
                ${rgb("2948FC")}§l§oM
                ${rgb("2E4BFC")}§l§oE
                ${rgb("344FFC")}§l§oT
                ${rgb("3952FD")}§l§oA
                ${rgb("3E56FD")}§l§oL
                ${rgb("4359FD")}§l§oI
                ${rgb("485DFD")}§l§oS
                ${rgb("4E60FD")}§l§oM 
                ${rgb("5868FE")}§l§oD
                ${rgb("5D6BFE")}§l§oA
                ${rgb("626FFE")}§l§oI
                ${rgb("6872FE")}§l§oL
                ${rgb("6D76FE")}§l§oY 
                ${rgb("777DFE")}§l§oS
                ${rgb("7C80FF")}§l§oH
                ${rgb("8284FF")}§l§oO
                ${rgb("8787FF")}§l§oP
                ${rgb("8C8BFF")}§l§o]
            """.trimIndent().replace("\n", "")
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        gui.fi(10, "experience_bottle", "경험치 병", 4000, buyTimes[0], 20)
        gui.fi(11, "firework_rocket", "폭죽 로켓", 4000, buyTimes[1], 20)
        gui.fi(12, "apple", "사과", 1000, buyTimes[2], 10)
        gui.fi(13, "ghast_tear", "가스트의 눈물", 10000, buyTimes[3], 1)
        gui.fi(14, "chest", "랜덤 복권", 10000, buyTimes[4], 1)
        gui.fi(15, "book", "1레벨 증가", 15000, buyTimes[5], 1)
        gui.fi(16, "gold_ingot", "10,000골드 지급", 0, buyTimes[6], 1)

        player.openInventory(gui)
        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1f, 1f)
    }
}