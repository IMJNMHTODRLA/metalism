package _RedGold__.main.command.boost.sys.plusTools.ggColorGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Gui.getItem
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin

class GgColorGui(private val plugin: JavaPlugin) {
    private fun Inventory.fi(n: Int, title: String, set: Long, value: Long, isVipRank: Boolean) {
        this.setItem(n, getItem(
            "writable_book",
            title + if (set == value) " &8&l[&a&l선택됨&8&l]" else "",
            listOf(
                "&f",
                "&a&l[좌클릭]: &f&l장착",
                "&a&l[우클릭]: &f&l미리보기",
                "",
                "&8적용 되는 메시지: GG",
                "",
                if (isVipRank) "" else "&cVIP 전용 설정입니다."
            )
        ))
    }

    private fun Inventory.nn(n: Int) {
        this.setItem(n, getItem(
            "barrier",
            "&c&l장착할 수 있는 색깔이 아닙니다.",
            listOf(
                "&f",
                "&a&l[좌클릭]: &f&l장착",
                "&b&l[우클릭]: &f&l미리보기",
                ""
            )
        ))
    }

    fun openGui(player: Player, page: Int) {
        val isPlusRank = player.hasPermission("Main.plus")
        val ggColor = getData(plugin, player, "gg_color").toLong()

        val gui = GgColorHolder(page, isPlusRank).inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            """
                ${rgb("2444FC")}§l§o[
                ${rgb("2B49FC")}§l§oM
                ${rgb("324DFC")}§l§oE
                ${rgb("3952FD")}§l§oT
                ${rgb("4057FD")}§l§oA
                ${rgb("475CFD")}§l§oL
                ${rgb("4E60FD")}§l§oI
                ${rgb("5565FD")}§l§oS
                ${rgb("5B6AFE")}§l§oM 
                ${rgb("6973FE")}§l§oB
                ${rgb("7078FE")}§l§oO
                ${rgb("777DFE")}§l§oO
                ${rgb("7E82FF")}§l§oS
                ${rgb("8586FF")}§l§oT
                ${rgb("8C8BFF")}§l§o]
            """.trimIndent().replace("\n", "")
        )

        val background2 = getItem(
            "black_stained_glass_pane",
            """
                ${rgb("2444FC")}§l§o[
                ${rgb("2B49FC")}§l§oM
                ${rgb("324DFC")}§l§oE
                ${rgb("3952FD")}§l§oT
                ${rgb("4057FD")}§l§oA
                ${rgb("475CFD")}§l§oL
                ${rgb("4E60FD")}§l§oI
                ${rgb("5565FD")}§l§oS
                ${rgb("5B6AFE")}§l§oM 
                ${rgb("6973FE")}§l§oB
                ${rgb("7078FE")}§l§oO
                ${rgb("777DFE")}§l§oO
                ${rgb("7E82FF")}§l§oS
                ${rgb("8586FF")}§l§oT
                ${rgb("8C8BFF")}§l§o]
            """.trimIndent().replace("\n", "")
        )

        gui.setItem(31, getItem(
            "book",
            "&8&l현재 페이지: ($page/2)",
        ))

        for (i in 0 until gui.size) gui.setItem(i, background)
        for (i in 27 until gui.size) gui.setItem(i, background2)

        if (page == 1) {
            gui.fi(10, "&f기본", ggColor, 0, true)
            gui.fi(11, "&0&l검은색", ggColor, 1, isPlusRank)
            gui.fi(12, "&1&l어두운 파란색", ggColor, 2, isPlusRank)
            gui.fi(13, "&2&l어두운 초록색", ggColor, 3, isPlusRank)
            gui.fi(14, "&3&l어두운 청록색", ggColor, 4, isPlusRank)
            gui.fi(15, "&4&l어두운 빨간색", ggColor, 5, isPlusRank)
            gui.fi(16, "&5&l어두운 보라색", ggColor, 6, isPlusRank)

            gui.fi(19, "&6&l황금색", ggColor, 7, isPlusRank)
            gui.fi(20, "&7&l회색", ggColor, 8, isPlusRank)
            gui.fi(21, "&8&l어두운 회색", ggColor, 9, isPlusRank)
            gui.fi(22, "&9&l파란색", ggColor, 10, isPlusRank)
            gui.fi(23, "&a&l초록색", ggColor, 11, isPlusRank)
            gui.fi(24, "&b&l밝은 청록색", ggColor, 12, isPlusRank)
            gui.fi(25, "&c&l빨간색", ggColor, 13, isPlusRank)

            gui.setItem(35, getItem(
                "green_stained_glass_pane",
                "&a&l다음 페이지로 이동"
            ))
        } else {
            gui.fi(10, "&d&l밝은 보라색", ggColor, 14, isPlusRank)
            gui.fi(11, "&e&l노란색", ggColor, 15, isPlusRank)
            gui.fi(12, "&f&l하얀색", ggColor, 16, isPlusRank)
            gui.nn(13)
            gui.nn(14)
            gui.nn(15)
            gui.nn(16)

            gui.nn(19)
            gui.nn(20)
            gui.nn(21)
            gui.nn(22)
            gui.nn(23)
            gui.nn(24)
            gui.nn(25)

            gui.setItem(27, getItem(
                "red_stained_glass_pane",
                "&c&l이전 페이지로 이동"
            ))
        }

        player.openInventory(gui)
        player.playSound(player.location, Sound.BLOCK_CHEST_OPEN, 1f, 1f)
    }
}