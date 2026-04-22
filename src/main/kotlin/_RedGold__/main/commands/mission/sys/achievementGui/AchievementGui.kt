package _RedGold__.main.commands.mission.sys.achievementGui

import _RedGold__.main.functions.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.functions.Gui.getItem
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class AchievementGui(private val plugin: JavaPlugin) {
    private val prefix = """
        ${rgb("2444FC")}§l§o[
        ${rgb("2A48FC")}§l§oM
        ${rgb("304CFC")}§l§oE
        ${rgb("3651FD")}§l§oT
        ${rgb("3C55FD")}§l§oA
        ${rgb("4359FD")}§l§oL
        ${rgb("495DFD")}§l§oI
        ${rgb("4F61FD")}§l§oS
        ${rgb("5565FD")}§l§oM 
        ${rgb("616EFE")}§l§oM
        ${rgb("6772FE")}§l§oI
        ${rgb("6D76FE")}§l§oS
        ${rgb("747AFE")}§l§oS
        ${rgb("7A7EFE")}§l§oI
        ${rgb("8083FF")}§l§oO
        ${rgb("8687FF")}§l§oN
        ${rgb("8C8BFF")}§l§o]
    """.trimIndent().replace("\n", "")

    fun openGui(player: Player, page: Int, sendSound: Float = 1f) {
        val progressList = mutableListOf<Int>()
        val getList = mutableListOf<Boolean>()
        for (i in 0..15) {
            progressList.add(getData(plugin, player, "mission/achievement/progress/$i").toInt())
            getList.add(getData(plugin, player, "mission/achievement/get/$i") == "1")
        }

        val gui = AchievementHolder(page, progressList, getList).inventory

        fun i(n: Int, t: Int, name: String, max: Int, reward: String) {
            val id =
                if (getList[t]) "netherite_ingot"
                else if (progressList[t] >= max) "gold_ingot"
                else "gold_nugget"

            val title =
                if (getList[t]) "&f&l$name &a&l클리어 완료! (&7&l이미 보상을 획득 하였습니다.)"
                else if (progressList[t] >= max) "&f&l$name &a&l클리어 완료!&7&l (보상 획득이 가능합니다.)"
                else "&f&l$name &e&l(${progressList[t]}/$max)"

            gui.setItem(n, getItem(
                id,
                title,
                listOf(
                    "&f&l",
                    prefix,
                    "&6&l보상:",
                    "   $reward",
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
            "&c이전 페이지로 이동(${page - 1})"
        ))

        gui.setItem(53, getItem(
            "green_stained_glass_pane",
            "&a다음 페이지로 이동(${page + 1})"
        ))

        when (page) {
            0 -> {
                i(10, 0, "일일 접속을 50회", 50, "&b&l40 캐시 지급")
                i(11, 1, "일일 접속을 100회", 100, "&b&l40 캐시 지급")
                i(12, 2, "일일 접속을 150회", 150, "&b&l40 캐시 지급")
                i(13, 3, "일일 접속을 200회", 200, "&a&l[시간의 연속] 칭호 지급, &b&l80 캐시 지급")
                i(14, 4, "플레이어 처치를 50회", 50, "&b&l80 캐시 지급")
                i(15, 5, "플레이어 처치를 100회", 100, "&4&l[킬러] 칭호 지급, &b&l80 캐시 지급")
                i(16, 6, "월간 상점 아이템 구매를 6회", 6, "&b&l80 캐시 지급")

                i(19, 7, "월간 상점 아이템 구매를 12회", 12, "&d&l[컬렉션] 칭호 지급, &b&l80 캐시 지급")
                i(20, 8, "엔더진주 사용을 100회", 100, "&b&l20 캐시 지급")
                i(21, 9, "엔더진주 사용을 200회", 200, "&b&l20 캐시 지급")
                i(22, 10, "블록 설치를 2,500회", 2_500, "&b&l40 캐시 지급")
                i(23, 11, "블록 설치를 5,000회", 5_000, "&b&l40 캐시 지급")
                i(24, 12, "블록 설치를 7,500회", 7_500, "&b&l40 캐시 지급")
                i(25, 13, "블록 설치를 10,000회", 10_000, "&8&l[무게 변화] 칭호 지급, &b&l80 캐시 지급")

                i(28, 14, "블록 파괴를 2,500회", 2_500, "&b&l40 캐시 지급")
                i(29, 15, "블록 파괴를 5,000회", 5_000, "&b&l40 캐시 지급")
                i(30, 16, "블록 파괴를 7,500회", 7_500, "&b&l40 캐시 지급")
                i(31, 17, "블록 파괴를 10,000회", 10_000, "&8&l[지각 변동] 칭호 지급, &b&l80 캐시 지급")
                i(32, 18, "엔드 수정 폭팔을 100회", 100, "&b&l20 캐시 지급")
                i(33, 19, "엔드 수정 폭팔을 150회", 150, "&b&l20 캐시 지급")
                i(34, 20, "엔드 수정 폭팔을 200회", 200, "&b&l20 캐시 지급")

                gui.setItem(45, background1)
            }
            1 -> {
                i(10, 21, "불사의 토템 발동을 100회", 100, "&b&3l20 캐시 지급")
                i(11, 22, "불사의 토템 발동을 200회", 200, "&b&l20 캐시 지급")
                i(12, 23, "황금 사과 섭취를 100회", 100, "&b&l20 캐시 지급")
                i(13, 24, "황금 사과 섭취를 200회", 200, "&b&l20 캐시 지급")
                i(14, 25, "황금 사과 섭취를 300회", 300, "&b&l20 캐시 지급")
                i(15, 26, "위더 처치를 10회", 10, "&b&l40 캐시 지급")
                i(16, 27, "위더 처치를 20회", 20, "&b&l40 캐시 지급")

                i(19, 28, "위더 처치를 30회", 30, "&b&l40 캐시 지급")

                gui.setItem(53, background1)
            }
        }

        gui.setItem(48, getItem(
            "emerald",
            "&a&l일일 미션"
        ))

        gui.setItem(49, getItem(
            "diamond",
            "&e&l주간 미션"
        ))

        gui.setItem(50, getItem(
            "dragon_egg",
            "&d&l업적 미션"
        ).apply {addUnsafeEnchantment(Enchantment.LUCK_OF_THE_SEA, 69)})

        player.playSound(player.location, Sound.UI_LOOM_TAKE_RESULT, sendSound, 1f)
        player.openInventory(gui)
    }
}