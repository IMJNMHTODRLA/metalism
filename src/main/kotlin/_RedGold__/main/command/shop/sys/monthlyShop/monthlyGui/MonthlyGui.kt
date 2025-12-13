package _RedGold__.main.command.shop.sys.monthlyShop.monthlyGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.defData
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.api.toFormat
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin

class MonthlyGui(private val plugin: JavaPlugin) {
    private fun Inventory.fi(n: Int, id: String, title: String, pur: Int, isBuy: Boolean) {
        this.setItem(n, getItem(
            id,
            "&f&l$title",
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
                "&a&l[구매(좌클릭)] &f&l구매가: ${pur.toFormat()}원",
                "",
                if (isBuy) "&c&l구매 할 수 없습니다. 다음 달에 다시 시도해주세요." else "&c&l한 달 마다 한 제품만 1회 구매 가능합니다!"
            ))
        )
    }

    fun openGui(player: Player) {
        val isBuy = getData(plugin, player, "monthly_shop").toInt() == 1

        val gui = MonthlyHolder(isBuy).inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            """
                ${rgb("2444FC")}§l§o[
                ${rgb("2947FC")}§l§oM
                ${rgb("2D4AFC")}§l§oE
                ${rgb("324EFC")}§l§oT
                ${rgb("3751FD")}§l§oA
                ${rgb("3C54FD")}§l§oL
                ${rgb("4057FD")}§l§oI
                ${rgb("455BFD")}§l§oS
                ${rgb("4A5EFD")}§l§oM 
                ${rgb("5364FD")}§l§oM
                ${rgb("5868FE")}§l§oO
                ${rgb("5D6BFE")}§l§oN
                ${rgb("616EFE")}§l§oT
                ${rgb("6671FE")}§l§oH
                ${rgb("6B74FE")}§l§oL
                ${rgb("7078FE")}§l§oY 
                ${rgb("797EFE")}§l§oS
                ${rgb("7E81FF")}§l§oH
                ${rgb("8385FF")}§l§oO
                ${rgb("8788FF")}§l§oP
                ${rgb("8C8BFF")}§l§o]
            """.trimIndent().replace("\n", "")
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        gui.fi(12, "elytra", "겉날개", 1200000, isBuy)
        gui.fi(13, "shulker_box", "셜커 상자", 1000000, isBuy)
        gui.fi(14, "name_tag", "이름표", 100000, isBuy)

        player.openInventory(gui)
        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1f, 1f)
    }
}