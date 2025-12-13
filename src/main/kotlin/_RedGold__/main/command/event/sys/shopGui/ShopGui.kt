package _RedGold__.main.command.event.sys.shopGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class ShopGui {
    private val prefix = """
        ${rgb("2444FC")}§l§o[
        ${rgb("2B49FC")}§l§oM
        ${rgb("324DFC")}§l§oE
        ${rgb("3952FD")}§l§oT
        ${rgb("4057FD")}§l§oA
        ${rgb("475CFD")}§l§oL
        ${rgb("4E60FD")}§l§oI
        ${rgb("5565FD")}§l§oS
        ${rgb("5B6AFE")}§l§oM 
        ${rgb("6973FE")}§l§oE
        ${rgb("7078FE")}§l§oV
        ${rgb("777DFE")}§l§oE
        ${rgb("7E82FF")}§l§oN
        ${rgb("8586FF")}§l§oT
        ${rgb("8C8BFF")}§l§o]
    """.trimIndent().replace("\n", "")

    fun openGui(player: Player) {
        val gui = ShopHolder().inventory

        fun f(n: Int, id: String, title: String, pur: Int, advanced: Int? = null) {
            gui.setItem(n, getItem(
                id,
                "&f&l$title",
                listOf(
                    "&f",
                    prefix,
                    if (advanced == null) "&a&l[구매(좌클릭)] &f&l구매가: $pur 토큰" else "&a&l[구매(좌클릭)] &f&l구매가: $pur 고급 토큰"
                )
            ))
        }

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

        f(10, "enchanted_book", "발사체로부터 보호 II", 200)
        f(11, "enchanted_book", "보호 II", 200)
        f(12, "totem_of_undying", "불사의 토템", 200)
        f(13, "wither_rose", "위더 장미", 100)
        f(14, "enchanted_book", "강타 II", 200)
        f(15, "potion", "신속 II (1:00)", 400)
        f(16, "splash_potion", "즉시 치유 II", 500)

        f(19, "potion", "힘 I (0:50)", 200)
        f(20, "potion", "힘 II (1:30)", 0, 50)
        f(21, "splash_potion", "위더 II (0:40)", 0, 80)
        f(21, "splash_potion", "속도 감소 IV (1:00)", 0, 50)
        f(23, "splash_potion", "나약함 III (1:00)", 0, 80)
        f(24, "potion", "재생 III (1:00)", 0, 80)
        f(24, "potion", "저항 II (1:30)", 0, 100)

        player.openInventory(gui)
        player.playSound(player.location, Sound.UI_BUTTON_CLICK, 1f, 1f)
    }
}