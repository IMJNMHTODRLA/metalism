package _RedGold__.main.event.randomEffect.selectGui

import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.max
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player

class SelectGui {
    fun openGui(player: Player) {
        val gui = SelectHolder(System.currentTimeMillis() / 1000).inventory

        val background = getItem(
            "light_blue_stained_glass_pane",
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
                ${rgb("6973FE")}§l§oE
                ${rgb("7078FE")}§l§oV
                ${rgb("777DFE")}§l§oE
                ${rgb("7E82FF")}§l§oN
                ${rgb("8586FF")}§l§oT
                ${rgb("8C8BFF")}§l§o]
            """.trimIndent().replace("\n", "")
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        val uuid = player.uniqueId

        fun fi(n: Int, item: String, title: String, change: List<String>, isEnchant: Boolean = false) {
            val isSelectTimeLast =
                if (max[uuid] == 2) "&c&l오늘의 마지막 선택입니다."
                else "&e&l선택 횟수가 ${3 - (max[uuid]!!)}번 남았습니다."

            gui.setItem(n, getItem(
                item,
                "$title $isSelectTimeLast",
                change
            ).apply {if (isEnchant) addUnsafeEnchantment(Enchantment.SHARPNESS, 8)})
        }

        fi(11, "iron_sword", "&e&l보통(Normal)", listOf(
            "&f&l난이도 완료 점수&8&l\\&&f&l보상: &d&l100,000 점수&8&l, &b&l0 캐시&8&l(30분 생존 시 지급)",
            "&f&l5분 주기 점수: &d&l10,000 점수",
            "",
            "&8&l[&c&l패널티&8&l]",
            "&c&l허기 V",
            "",
            "&c&l2분 안에 선택 해야 합니다! &a&l빠르게 선택 할수록 추가 점수가 지급됩니다.(최대 60,000 점수)",
        ))

        fi(12, "diamond_sword", "&c&l어려움(Hard)", listOf(
            "&f&l난이도 완료 점수&8&l\\&&f&l보상: &d&l150,000 점수&8&l, &b&l0 캐시&8&l(30분 생존 시 지급)",
            "&f&l5분 주기 점수: &d&l15,000 점수",
            "",
            "&8&l[&c&l패널티&8&l]",
            "&f&l허기 V",
            "&c&l구속 II",
            "&c&l채굴 피로 I",
            "",
            "&c&l2분 안에 선택 해야 합니다! &a&l빠르게 선택 할수록 추가 점수가 지급됩니다.(최대 60,000 점수)",
        ))

        fi(13, "netherite_sword", "&4&l하드코어(HardCore)", listOf(
            "&f&l난이도 완료 점수&8&l\\&&f&l보상: &d&l300,000 점수&8&l, &b&l3 캐시&8&l(30분 생존 시 지급)",
            "&f&l5분 주기 점수: &d&l30,000 점수",
            "&a&l몬스터 처치 시간 점수: &d&l최대 120,000 점수 &8&l(스캘레톤 처치 시 지급, 매초 400점 감소)",
            "",
            "&8&l[&c&l패널티&8&l]",
            "&8&l - 어려움 난이도의 모든 패널티",
            "&c&l5분 마다 스캘레톤 소환/체력 200칸/신속 I/화염 저항 I/5분 안에 못 죽일 시 5분 주기 점수 0.5배로 지급",
            "&8&l - 방어구: 보호 III 다이아 풀/힘 III, 밀어내기 II 활",
            "",
            "&c&l2분 안에 선택 해야 합니다! &a&l빠르게 선택 할수록 추가 점수가 지급됩니다.(최대 60,000 점수)",
        ))

        fi(14, "netherite_axe", "&b&l익스트림(Extreme)", listOf(
            "&f&l난이도 완료 점수&8&l\\&&f&l보상: &d&l450,000 점수&8&l, &b&l4 캐시&8&l(30분 생존 시 지급)",
            "&f&l5분 주기 점수: &d&l50,000 점수",
            "&e&l몬스터 처치 시간 점수: &d&l최대 120,000 점수 &8&l(스캘레톤 처치 시 지급, 매초 400점 감소)",
            "",
            "&8&l[&c&l패널티&8&l]",
            "&8&l - 어려움 난이도의 모든 패널티",
            "&f&l5분 마다 스캘레톤 소환/체력 200칸/신속 I/화염 저항 I/5분 안에 못 죽일 시 5분 주기 점수 0.5배로 지급",
            "&8&l - 방어구: 보호 III 다이아 풀/힘 III, 밀어내기 II 활",
            "&c&l나약함 II",
            "",
            "&c&l2분 안에 선택 해야 합니다! &a&l빠르게 선택 할수록 추가 점수가 지급됩니다.(최대 60,000 점수)",
        ), true)

        fi(15, "mace", "&d&l얼티밋(Ultimate)", listOf(
            "&f&l난이도 완료 점수&8&l\\&&f&l보상: &d&l550,000 점수&8&l, &b&l6 캐시&8&l(30분 생존 시 지급)",
            "&f&l5분 주기 점수: &d&l60,000 점수",
            "&f&l몬스터 처치 시간 점수: &d&l최대 120,000 점수 &8&l(스캘레톤 처치 시 지급, 매초 400점 감소)",
            "&a&l2차 몬스터 처치 시간 점수: &d&l최대 240,000 점수 &8&l(좀비 처치 시 지급, 매초 800점 감소)",
            "",
            "&8&l[&c&l패널티&8&l]",
            "&8&l - 어려움 난이도의 모든 패널티",
            "&f&l5분 마다 스캘레톤 소환/체력 200칸/신속 I/화염 저항 I/5분 안에 못 죽일 시 5분 주기 점수 0.5배로 지급",
            "&8&l - 방어구: 보호 III 다이아 풀/힘 III, 밀어내기 II 활",
            "&f&l나약함 II",
            "&c&l시듦 IV",
            "&c&l5분 마다 좀비 소환/체력 400칸/신속 I/화염 저항 I/힘 I/5분 안에 못 죽일 시 체력 9칸 깎임",
            "&8&l - 방어구: 보호 III 다이아 풀/날카로움 III 다이아 검",
            "",
            "&c&l2분 안에 선택 해야 합니다! &a&l빠르게 선택 할수록 추가 점수가 지급됩니다.(최대 60,000 점수)",
        ), true)

        fi(22, "barrier", "&7&l선택안함(Nothing)", listOf(
            "",
            "&8&l선택 횟수는 증가 안합니다."
        ))

        player.openInventory(gui)
        player.playSound(player.location, Sound.AMBIENT_CAVE, 1f, 1f)
    }
}