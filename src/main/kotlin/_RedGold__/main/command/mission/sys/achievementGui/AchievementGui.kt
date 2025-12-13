package _RedGold__.main.command.mission.sys.achievementGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Gui.getItem
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class AchievementGui(private val plugin: JavaPlugin) {
    fun openGui(player: Player, sendSound: Float = 1f) {
        val progressList = mutableListOf<Int>()
        val getList = mutableListOf<Boolean>()
        for (i in 0..15) {
            progressList.add(getData(plugin, player, "mission/achievement/progress/$i").toInt())
            getList.add(getData(plugin, player, "mission/achievement/get/$i") == "1")
        }

        val gui = AchievementHolder(progressList, getList).inventory

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
                    "&a",
                    "&7&l보상 획득 시, 전체 채팅에 알림으로 출력됩니다."
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

        i(10, 0, "일일 접속을 200회", 200, "&a&l[시간의 연속] 칭호 지급, &d&l효율 VII 네더라이트 곡괭이 지급")
        i(11, 1, "플레이어 처치를 200회", 200, "&4&l[킬러] 칭호 지급, &d&l날카로움 VI 네더라이트 검 지급")
        i(12, 2, "흑요석 설치를 300회", 300, "&b&l10 캐시 지급, &d&l엔드 수정 64개 지급")
        i(13, 3, "월간 상점 아이템 구매를 12회", 12, "&b&l200 캐시 지급, &d&l마법이 부여된 황금 사과 3개 지급")
        i(14, 4, "월간 상점 아이템 구매를 24회", 24, "&d&l[컬렉션] 칭호 지급, &b&l2,000 캐시 지급")
        i(15, 5, "모루 손상을 50회", 50, "&b&l10 캐시 지급, &a&l경험치 병 128개 지급")
        i(16, 6, "TNT 점화를 300회", 300, "&b&l20 캐시 지급, &c&l크리퍼 생성 알 16개 지급")

        i(19, 7, "마법이 부여된 황금 사과 섭취를 10회", 10, "&d&l행운 V 곡괭이 지급")
        i(20, 8, "엔더진주 사용을 100회", 100, "&6&l200,000 골드 지급, &d&l겉날개 2개 지급")
        i(21, 9, "블록 설치를 10,000회", 10_000, "&8&l[무게 변화] 칭호 지급, &6&l2,000,000 골드 지급, &b&l500 캐시 지급")
        i(22, 10, "블록 파괴를 10,000회", 10_000, "&8&l[지각 변동] 칭호 지급, &6&l2,500,000 골드 지급, &b&l550 캐시 지급")
        i(23, 11, "엔드 수정 폭팔을 400회", 400, "&b&l20 캐시 지급, &c&l크리퍼 생성 알 18개 지급")
        i(24, 12, "불사의 토템 발동을 200회", 200, "&d&l폭팔로부터 보호 V 네더라이트 레깅스 지급")
        i(25, 13, "황금 사과 섭취를 300회", 300, "&b&l30 캐시 지급, &e&l마법이 부여된 황금 사과 1개 지급")

        i(28, 14, "위더 처치를 20회", 20, "&e&l강타 VI 네더라이트 검 지급")
        i(29, 15, "위더 처치를 30회", 30, "&6&l1,000,000 골드 지급")

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