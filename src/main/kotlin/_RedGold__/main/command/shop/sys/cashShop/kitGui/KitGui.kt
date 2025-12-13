package _RedGold__.main.command.shop.sys.cashShop.kitGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.sys.Chat.ChatApply.symmetry
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin

class KitGui(private val plugin: JavaPlugin) {
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

    fun openGui(player: Player, sound: Float = 1f) {
        val buyTimes = mutableListOf<Int>()
        for (i in 0..3) buyTimes.add(getData(plugin, player, "kit_shop/$i").toInt())

        val gui = KitHolder(buyTimes).inventory

        fun Int.fi(type: Int, pur: Int) {
            val typeItem = when (type) {
                0 -> "red_shulker_box" //PVP 키트
                1 -> "purple_shulker_box" //CPVP 키트
                2 -> "orange_shulker_box" //레드스톤 키트
                else -> "green_shulker_box" //건축 키트
            }

            val title = when (type) {
                0 -> "PVP 키트" //PVP 키트
                1 -> "CPVP 키트" //CPVP 키트
                2 -> "물약 키트" //물약 키트
                else -> "방해 키트" //함정 키트
            }

            gui.setItem(this, getItem(
                typeItem,
                "&f&l$title &8구매 횟수: (${buyTimes[type]}/2)",
                listOf(
                    "&f",
                    prefix,
                    "&a&l[구매(좌클릭)] &f&l구매가: ${pur.toFormat()}캐시",
                    "&a&l[미리보기(우클릭)] &7&l우클릭 시 아이템 구성을 볼 수 있습니다.",
                    "&f",
                    "&c&l매 주마다 구매 횟수가 초기화 됩니다."
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
        for (i in 18 until gui.size) gui.setItem(i, background2)

        gui.setItem(22, getItem(
            "book",
            "&8&l현재 페이지: (1/1)",
        ))

        10.fi(0, 250)
        12.fi(1, 250)
        14.fi(2, 150)
        16.fi(3, 200)

        player.openInventory(gui)
        player.playSound(player.location, Sound.UI_BUTTON_CLICK, sound, 1f)
    }
}