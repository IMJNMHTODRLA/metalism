package _RedGold__.main.command.mission.sys.dailyGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Gui.getItem
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class DailyGui(private val plugin: JavaPlugin) {
    fun openGui(player: Player, sendSound: Float = 1f) {
        val progressList = mutableListOf<Int>()
        val getList = mutableListOf<Boolean>()
        for (i in 0..6) {
            progressList.add(getData(plugin, player, "mission/daily/progress/$i").toInt())
            getList.add(getData(plugin, player, "mission/daily/get/$i") == "1")
        }

        val gui = DailyHolder(progressList, getList).inventory

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
                    """
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
                    """.trimIndent().replace("\n", ""),
                    "&6&l보상:",
                    "   $reward",
                ))
            )
        }

        val background = getItem(
            "magenta_stained_glass_pane",
            """
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
        )

        val background1 = getItem(
            "black_stained_glass_pane",
            """
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
        )

        for (i in 0 until gui.size) gui.setItem(i, background)
        for (i in 45 until gui.size) gui.setItem(i, background1)

        i(19, 0, "일일 접속", 1, "&b&l1 캐시 지급")
        i(20, 1, "일일 상점 아이템 구매", 1, "&d&l엔드 수정 2개 지급")
        i(21, 2, "블록 파괴를 100회", 100, "&d&l리스폰 정박기 2개 지급")
        i(22, 3, "블록 설치를 100회", 100, "&a&l경험치 병 4개 지급")
        i(23, 4, "위더 스켈레톤 처치를 3회", 3, "&6&l5,000 골드 지급")
        i(24, 5, "플레이어 처치를 1회", 1, "&b&l3 캐시 지급")
        i(25, 6, "일일 미션을 5회 클리어", 5, "&6&l30,000 골드 지급, &a&l경험치 병 4개 지급")

        gui.setItem(48, getItem(
            "emerald",
            "&a&l일일 미션"
        ).apply {addUnsafeEnchantment(Enchantment.LUCK_OF_THE_SEA, 69)})

        gui.setItem(49, getItem(
            "diamond",
            "&e&l주간 미션"
        ))

        gui.setItem(50, getItem(
            "dragon_egg",
            "&d&l업적 미션"
        ))

        player.playSound(player.location, Sound.UI_LOOM_TAKE_RESULT, sendSound, 1f)
        player.openInventory(gui)
    }
}