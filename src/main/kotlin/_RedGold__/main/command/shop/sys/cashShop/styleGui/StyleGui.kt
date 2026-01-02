package _RedGold__.main.command.shop.sys.cashShop.styleGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.sys.Chat.ChatApply.MAX_STYLE
import _RedGold__.main.sys.Chat.ChatApply.symmetry
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin

class StyleGui(private val plugin: JavaPlugin) {
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
        ${rgb("5B69FE")}§l§oC
        ${rgb("606DFE")}§l§oA
        ${rgb("6671FE")}§l§oS
        ${rgb("6B75FE")}§l§oH 
        ${rgb("767CFE")}§l§oS
        ${rgb("7C80FF")}§l§oH
        ${rgb("8184FF")}§l§oO
        ${rgb("8787FF")}§l§oP
        ${rgb("8C8BFF")}§l§o]
    """.trimIndent().replace("\n", "")

    private fun Inventory.none(n: Int) {
        this.setItem(n, getItem(
            "barrier",
            "&c&l판매하고 있는 아이템이 아닙니다.",
            listOf(
                "&f",
                prefix,
                "&c&l[구매 불가]",
                "&c&l[판매 불가]",
            )
        ))
    }

    fun openGui(player: Player, sound: Float = 1f) {
        val isBuy = mutableListOf<Boolean>()
        for (i in MAX_STYLE..18) isBuy.add(getData(plugin, player, "style/$i") == "1")

        val gui = StyleHolder(isBuy).inventory

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

        val purVal = listOf(
            1400, 2200, 1200, 1400, 800, 800, 1000,
            1200, 800, 1200, 1200, 1300, 1300, 1300
        )

        fun Int.fi(id: Int) {
            val isBuyTitle = if (isBuy[id]) "&a&l[구매 완료]" else ""

            gui.setItem(this, getItem(
                if (isBuy[id]) "writable_book" else "book",
                "${symmetry[MAX_STYLE + id]} $isBuyTitle",
                listOf(
                    "&f",
                    prefix,
                    if (!isBuy[id]) "&a&l[구매(좌클릭)] &f&l구매가: ${purVal[id].toFormat()}캐시" else "&a&l[구매 완료]",
                    "&f",
                    if (!isBuy[id]) "&8&l칭호 선택에서 미리보기가 가능합니다." else "&8&l클릭 시 칭호 선택 창을 이동됩니다."
                )
            ).apply {if (isBuy[id]) addUnsafeEnchantment(Enchantment.UNBREAKING, 5)})
        }

        10.fi(0)
        11.fi(1)
        12.fi(2)
        13.fi(3)
        14.fi(4)
        15.fi(5)
        16.fi(6)

        19.fi(7)
        20.fi(8)
        21.fi(9)
        22.fi(10)
        23.fi(11)
        24.fi(12)
        25.fi(13)

        player.openInventory(gui)
        player.playSound(player.location, Sound.UI_BUTTON_CLICK, sound, 1f)
    }
}