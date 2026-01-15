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
        for (i in 0..55) getList.add(getData(plugin, player, "randomEffect/get/$i") == "1")

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
                    "   $reward"
                )).apply { if (t % 3 == 0) addUnsafeEnchantment(Enchantment.LUCK_OF_THE_SEA, 5)}
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

        gui.setItem(53, getItem(
            "green_stained_glass_pane",
            "&a&l다음 페이지로 이동",
        ))


        gui.setItem(49, getItem(
            "book",
            "&8&l현재 페이지: ($page/1)",
        ))

        when (page) {
            1 -> {
                i(10, 0, 100_000, "&a&l1 레벨 증가")
                i(11, 1, 200_000, "&6&l300,000 골드 지급")
                i(12, 2, 300_000, "&d&l엔드 수정 16개 지급")
                i(13, 3, 400_000, "&d&l리스폰 정박기 16개 지급")
                i(14, 4, 500_000, "&d&l엔더 진주 16개 지급")
                i(15, 5, 600_000, "&a&l경험치 병 16개 지급")
                i(16, 6, 700_000, "&b&l5 캐시 지급")

                i(19, 7, 800_000, "&a&l1 레벨 증가")
                i(20, 8, 900_000, "&6&l500,000 골드 지급")
                i(21, 9, 1_000_000, "&d&l엔드 수정 24개 지급")
                i(22, 10, 1_500_000, "&d&l리스폰 정박기 24개 지급")
                i(23, 11, 2_000_000, "&d&l황금 당근 24개 지급")
                i(24, 12, 2_500_000, "&a&l경험치 병 24개 지급")
                i(25, 13, 3_000_000, "&b&l10 캐시 지급")

                i(28, 14, 3_500_000, "&a&l2 레벨 증가")
                i(29, 15, 4_000_000, "&6&l1,000,000 골드 지급")
                i(30, 16, 4_500_000, "&d&l엔드 수정 48개 지급")
                i(31, 17, 5_000_000, "&d&l불사의 토템 8개 지급")
                i(32, 18, 6_000_000, "&d&l황금 당근 48개 지급")
                i(33, 19, 7_000_000, "&a&l경험치 병 48개 지급")
                i(34, 20, 8_000_000, "&b&l50 캐시 지급")

                i(37, 21, 9_000_000, "&a&l3 레벨 증가")
                i(38, 22, 10_000_000, "&6&l2,500,000 골드 지급")
                i(39, 23, 12_000_000, "&d&l엔드 수정 64개 지급")
                i(40, 24, 14_000_000, "&d&l불사의 토템 12개 지급")
                i(41, 25, 16_000_000, "&d&l황금 당근 64개 지급")
                i(42, 26, 18_000_000, "&a&l경험치 병 64개 지급")
                i(43, 27, 20_000_000, "&b&l50 캐시 지급") //

                gui.setItem(45, background1)
            }
            2 -> {
                i(10, 28, 22_000_000, "&d&l불사의 토템 16개 지급")
                i(11, 29, 24_000_000, "&d&l엔드 수정 16개 지급")
                i(12, 30, 26_000_000, "&d&l황금 당근 16개 지급")
                i(13, 31, 28_000_000, "&d&l경험치 병 16개 지급")
                i(14, 32, 30_000_000, "&d&l리스폰 정박기 16개 지급")
                i(15, 33, 32_000_000, "&d&l엔더 진주 16개 지급")
                i(16, 34, 34_000_000, "&b&l40 캐시 지급")

                i(19, 35, 36_000_000, "&6&l1,000,000 골드 지급")
                i(20, 36, 38_000_000, "&6&l1,000,000 골드 지급")
                i(21, 37, 40_000_000, "&6&l1,000,000 골드 지급")
                i(22, 38, 45_000_000, "&6&l1,000,000 골드 지급")
                i(23, 39, 50_000_000, "&6&l1,000,000 골드 지급")
                i(24, 40, 55_000_000, "&6&l1,000,000 골드 지급")
                i(25, 41, 60_000_000, "&b&l40 캐시 지급")

                i(28, 42, 65_000_000, "&6&l1,500,000 골드 지급")
                i(29, 43, 70_000_000, "&6&l1,500,000 골드 지급")
                i(30, 44, 75_000_000, "&6&l1,500,000 골드 지급")
                i(31, 45, 80_000_000, "&6&l1,500,000 골드 지급")
                i(32, 46, 85_000_000, "&6&l1,500,000 골드 지급")
                i(33, 47, 90_000_000, "&6&l1,500,000 골드 지급")
                i(34, 48, 95_000_000, "&b&l40 캐시 지급")

                i(37, 49, 100_000_000, "&a&l20 레벨 증가")
                i(38, 50, 110_000_000, "&d&l흑요석 64개 지급")
                i(39, 51, 120_000_000, "&d&l흑요석 64개 지급")
                i(40, 52, 130_000_000, "&d&l발광석 64개 지급")
                i(41, 53, 140_000_000, "&d&l발광석 64개 지급")
                i(42, 54, 150_000_000, "&d&l황금 당근 64개 지급")
                i(43, 55, 160_000_000, "&b&l100 캐시 지급") //

                gui.setItem(53, background1)
            }
        }

        player.playSound(player.location, Sound.UI_LOOM_TAKE_RESULT, sendSound, 1f)
        player.openInventory(gui)
    }
}