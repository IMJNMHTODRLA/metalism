package _RedGold__.main.command.style.sys

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.sys.Chat
import _RedGold__.main.sys.Chat.ChatApply.applyStyle
import _RedGold__.main.sys.Chat.ChatApply.symmetry
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin

class StyleGui(private val plugin: JavaPlugin) {
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
                            ${rgb("6973FE")}§l§oS
                            ${rgb("7078FE")}§l§oT
                            ${rgb("777DFE")}§l§oY
                            ${rgb("7E82FF")}§l§oL
                            ${rgb("8586FF")}§l§oE
                            ${rgb("8C8BFF")}§l§o]
                        """.trimIndent().replace("\n", "")

    private fun Inventory.none(n: Int) {
        val gui = this

        gui.setItem(n, getItem(
            "barrier",
            "&c&l존재하는 칭호가 아닙니다.",
            listOf(
                "&f",
                prefix,
                "&c&l[장착 불가]",
                "&c&l[미리보기 불가]",
            )
        ))
    }

    fun openGui(player: Player, sound: Float = 1f) {
        val isBuy = mutableListOf<Boolean>()
        for (i in 0..11) isBuy.add(getData(plugin, player, "style/$i") == "1")

        val isApply = applyStyle[player.uniqueId]?: -1

        fun Inventory.fi(n: Int, id: Int, whereGet: String) {
            val isSelect = if (isApply == id) "&a&l[선택됨]" else ""

            val list = if (isBuy[id]) {
                listOf(
                    "&f",
                    prefix,
                    "&a&l[좌클릭]: &f&l선택",
                    "&a&l[우클릭]: &f&l미리보기",
                )
            } else {
                listOf(
                    "&f",
                    prefix,
                    "&a&l[좌클릭]: &f&l${whereGet}(으)로 이동",
                    "&a&l[우클릭]: &f&l미리보기",
                    "&f",
                    "${symmetry[id]} &f&l칭호는 ${whereGet}에서 얻을 수 있습니다.",
                )
            }

            this.setItem(n, getItem(
                if (isBuy[id]) "writable_book" else "book",
                "${symmetry[id]} $isSelect",
                list
            ).apply {if (isApply == id) addUnsafeEnchantment(Enchantment.UNBREAKING, 5)})
        }

        val gui = StyleHolder(isBuy, isApply).inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            prefix
        )

        val background1 = getItem(
            "black_stained_glass_pane",
            prefix
        )

        for (i in 0..44) gui.setItem(i, background)
        for (i in 45..53) gui.setItem(i, background1)

        gui.setItem(49, getItem(
            "book",
            "&8&l현재 페이지: (1/1)",
        ))

        gui.setItem(10, getItem(
            "writable_book",
            "&f&l칭호 없음 ${if (isApply == -1) "&a&l[선택됨]" else ""}",
            listOf(
                "&f",
                prefix,
                "&a&l[좌클릭]: &f&l선택",
                "&a&l[우클릭]: &f&l미리보기",
            )
        ).apply {if (isApply == -1) addUnsafeEnchantment(Enchantment.UNBREAKING, 5)})

        gui.fi(11, 0, "업적 미션")
        gui.fi(12, 1, "업적 미션")
        gui.fi(13, 2, "업적 미션")
        gui.fi(14, 3, "업적 미션")
        gui.fi(15, 4, "업적 미션")
        gui.fi(16, 5, "칭호 상점")

        gui.fi(19, 6, "칭호 상점")
        gui.fi(20, 7, "칭호 상점")
        gui.fi(21, 8, "칭호 상점")
        gui.fi(22, 9, "칭호 상점")
        gui.fi(23, 10, "칭호 상점")
        gui.fi(24, 11, "칭호 상점")
        gui.fi(25, 12, "칭호 상점")

        gui.fi(28, 13, "칭호 상점")
        gui.fi(29, 14, "칭호 상점")
        gui.fi(30, 15, "칭호 상점")
        gui.fi(31, 16, "칭호 상점")
        gui.fi(32, 17, "칭호 상점")
        gui.fi(33, 18, "칭호 상점")
        gui.none(34)

        for (i in 37..43) gui.none(i)

        player.openInventory(gui)
        player.playSound(player.location, Sound.UI_BUTTON_CLICK, sound, 1f)
    }
}