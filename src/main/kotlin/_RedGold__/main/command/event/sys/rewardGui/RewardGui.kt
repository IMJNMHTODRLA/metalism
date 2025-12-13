package _RedGold__.main.command.event.sys.rewardGui

import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.point
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Gui.getItem
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class RewardGui(private val plugin: JavaPlugin) {
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

    fun openGui(player: Player, page: Int, sendSound: Float = 1f) {
        val getList = mutableListOf<Boolean>()
        val point = point[player.uniqueId]?: 0
        for (i in 0..38) getList.add(getData(plugin, player, "randomEffect/get/$i") == "1")

        val gui = RewardHolder(getList, page).inventory

        fun i(n: Int, t: Int, max: Long, reward: String) {
            val id =
                if (getList[t]) "netherite_ingot"
                else if (point >= max) "gold_ingot"
                else "gold_nugget"

            val title =
                if (getList[t]) "&8&l점수 보상 획득 완료"
                else if (point >= max) "&d&l점수 보상 &a&l획득 가능!"
                else "&f&l점수 보상 &e&l($point/${max})"

            gui.setItem(n, getItem(
                id,
                title,
                listOf(
                    "&f&l",
                    prefix,
                    "&6&l보상:",
                    "   $reward",
                    "&a",
                    "&f&l보상 획득 시&6&l100,000 골드&f&l와 &b&l10 캐시&f&l도 지급 됩니다."
                ))
            )
        }

        val background = getItem(
            "magenta_stained_glass_pane",
            prefix
        )

        val background1 = getItem(
            "black_stained_glass_pane",
            prefix
        )

        for (i in 0 until gui.size) gui.setItem(i, background)
        for (i in 45 until gui.size) gui.setItem(i, background1)

        gui.setItem(45, getItem(
            "red_stained_glass_pane",
            "&c&l이전 페이지로 이동",
        ))

        gui.setItem(49, getItem(
            "book",
            "&8&l현재 페이지: ($page/2)",
        ))

        gui.setItem(53, getItem(
            "green_stained_glass_pane",
            "&a&l다음 페이지로 이동",
        ))

        when (page) {
            1 -> {
                i(10, 0, 100_000, "&a&l경험치 병 4개 지급")
                i(11, 1, 200_000, "&d&l엔드 수정 4개 지급")
                i(12, 2, 300_000, "&b&l마법이 부여된 책(강타 II) 지급") //
                i(13, 3, 400_000, "&a&l1 레벨 증가")
                i(14, 4, 500_000, "&6&l100,000 골드 지급")
                i(15, 5, 800_000, "&b&l장식품 5연뽑 지급") //
                i(16, 6, 900_000, "&b&l10 캐시 지급")

                i(19, 7, 1_000_000, "&d&l리스폰 정박기 10개 지급")
                i(20, 8, 1_000_000, "&b&l마법이 부여된 책(강타 II) 지급") //
                i(21, 9, 1_000_000, "&2&l50 토큰 지급")
                i(22, 10, "&2&l60 토큰 지급")
                i(23, 11, "&2&l70 토큰 지급")
                i(24, 12, "&2&l80 토큰 지급, &a&l10 고급 토큰 지급")
                i(25, 13, "&2&l90 토큰 지급, &a&l10 고급 토큰 지급")

                i(28, 14, "&2&l100 토큰 지급, &a&l10 고급 토큰 지급")
                i(29, 15, "&2&l110 토큰 지급, &a&l10 고급 토큰 지급")
                i(30, 16, "&2&l120 토큰 지급, &a&l10 고급 토큰 지급")
                i(31, 17, "&2&l130 토큰 지급, &a&l10 고급 토큰 지급")
                i(32, 18, "&2&l140 토큰 지급, &a&l20 고급 토큰 지급")
                i(33, 19, "&2&l150 토큰 지급, &a&l30 고급 토큰 지급")
                i(34, 20, "&2&l160 토큰 지급, &a&l40 고급 토큰 지급")

                i(37, 21, "&2&l170 토큰 지급, &a&l50 고급 토큰 지급")
                i(38, 22, "&2&l180 토큰 지급, &a&l60 고급 토큰 지급")
                i(39, 23, "&2&l190 토큰 지급, &a&l70 고급 토큰 지급")
                i(40, 24, "&2&l200 토큰 지급, &a&l80 고급 토큰 지급")
                i(41, 25, "&2&l20 토큰 지급, &a&l90 고급 토큰 지급")
                i(42, 26, "&2&l20 토큰 지급, &a&l100 고급 토큰 지급")
                i(43, 27, "&2&l20 토큰 지급, &a&l10 고급 토큰 지급")

                gui.setItem(45, background1)
            }
            2 -> {
                i(10, 28, "&2&l20 토큰 지급, &a&l10 고급 토큰 지급")
                i(11, 29, "&2&l20 토큰 지급, &a&l10 고급 토큰 지급")
                i(12, 30, "&2&l20 토큰 지급, &a&l10 고급 토큰 지급")
                i(13, 31, "&2&l20 토큰 지급, &a&l10 고급 토큰 지급")
                i(14, 32, "&2&l20 토큰 지급, &a&l10 고급 토큰 지급")
                i(15, 33, "&2&l20 토큰 지급, &a&l10 고급 토큰 지급")
                i(16, 34, "&2&l20 토큰 지급, &a&l10 고급 토큰 지급")

                i(19, 35, "&2&l20 토큰 지급, &a&l10 고급 토큰 지급")
                i(20, 36, "&2&l20 토큰 지급, &a&l10 고급 토큰 지급")
                i(21, 37, "&2&l20 토큰 지급, &a&l10 고급 토큰 지급")
                i(22, 38, "&2&l20 토큰 지급, &a&l10 고급 토큰 지급")

                gui.setItem(53, background1)
            }
        }

        player.playSound(player.location, Sound.UI_LOOM_TAKE_RESULT, sendSound, 1f)
        player.openInventory(gui)
    }
}