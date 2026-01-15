package _RedGold__.main.event.randomEffect.selectGui

import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.cashingDifficulty
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.cashingPoint
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.max
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.api.toFormat
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player

class SelectGui {
    fun openGui(player: Player, time: Long? = null, sound: Float = 1f) {
        val sendTime = time?: (System.currentTimeMillis() / 1000)
        val gui = SelectHolder(sendTime).inventory

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

        val cashingPoint = (cashingPoint[uuid]?: 0).takeIf{it > 0}?.toFormat()?: "-"
        fun cashingMessage(difficulty: Int): String {
            return if (cashingPoint == "-" || cashingDifficulty[uuid] != difficulty) {
                "&c&l해당 난이도를 최소 한 번 클리어해야 합니다."
            } else {
                "&f&l${cashingPoint}점으로 소탕을 ${3 - max[uuid]!!}번 하실 수 있습니다."
            }
        }

        fi(11, "iron_sword", "&e&l보통(Normal)", listOf(
            "&f&l[선택(좌클릭)] &f&l클리어 시 소탕이 가능합니다.",
            "&f&l[소탕(우클릭)] ${cashingMessage(1)}",
            "",
            "&c&l2분 안에 선택 해야 합니다! &a&l빠르게 선택 할수록 추가 점수가 지급됩니다.(최대 60,000 점수)",
        ))

        gui.setItem(20, getItem(
            "emerald",
            "&8&l[&a&l보상(Normal)&8&l]",
            listOf(
                "&f&l난이도 완료 점수&8&l\\&&f&l보상: &d&l100,000 점수&8&l, &b&l0 캐시&8&l(30분 생존 시 지급)",
                "&f&l5분 주기 점수: &d&l10,000 점수"
            )
        ))

        gui.setItem(29, getItem(
            "book",
            "&8&l[&c&l패널티(Normal)&8&l]",
            listOf("&f&l허기 V")
        ))

        fi(12, "diamond_sword", "&c&l어려움(Hard)", listOf(
            "&f&l[선택(좌클릭)] &f&l클리어 시 소탕이 가능합니다.",
            "&f&l[소탕(우클릭)] ${cashingMessage(2)}",
            "",
            "&c&l2분 안에 선택 해야 합니다! &a&l빠르게 선택 할수록 추가 점수가 지급됩니다.(최대 60,000 점수)",
        ))

        gui.setItem(21, getItem(
            "emerald",
            "&8&l[&a&l보상(Hard)&8&l]",
            listOf(
                "&f&l난이도 완료 점수&8&l\\&&f&l보상: &d&l150,000 점수&8&l, &b&l0 캐시&8&l(30분 생존 시 지급)",
                "&f&l5분 주기 점수: &d&l15,000 점수",
            )
        ))

        gui.setItem(30, getItem(
            "book",
            "&8&l[&c&l패널티(Hard)&8&l]",
            listOf(
                "&f&l허기 V",
                "&c&l구속 II",
                "&c&l채굴 피로 I",
            )
        ))

        fi(13, "netherite_sword", "&4&l하드코어(HardCore)", listOf(
            "&f&l[선택(좌클릭)] &f&l클리어 시 소탕이 가능합니다.",
            "&f&l[소탕(우클릭)] ${cashingMessage(3)}",
            "",
            "&c&l2분 안에 선택 해야 합니다! &a&l빠르게 선택 할수록 추가 점수가 지급됩니다.(최대 60,000 점수)",
        ))

        gui.setItem(22, getItem(
            "emerald",
            "&8&l[&a&l보상(HardCore)&8&l]",
            listOf(
                "&f&l난이도 완료 점수&8&l\\&&f&l보상: &d&l300,000 점수&8&l, &b&l3 캐시&8&l(30분 생존 시 지급)",
                "&f&l5분 주기 점수: &d&l30,000 점수",
                "&a&l몬스터 처치 시간 점수: &d&l최대 180,000 점수 &8&l(스캘레톤 처치 시 지급, 10ms마다 4점 감소)",
            )
        ))

        gui.setItem(31, getItem(
            "book",
            "&8&l[&c&l패널티(HardCore)&8&l]",
            listOf(
                "&f&l허기 V",
                "&f&l구속 II",
                "&f&l채굴 피로 I",
                "&c&l5분 마다 스캘레톤 소환/체력 200칸/신속 I/화염 저항 I/5분 안에 못 죽일 시 5분 주기 점수 0.5배로 지급",
                "&8&l - 방어구: 보호 II 다이아 풀/힘 III, 밀어내기 II 활",
            )
        ))

        fi(14, "netherite_axe", "&b&l익스트림(Extreme)", listOf(
            "&f&l[선택(좌클릭)] &f&l클리어 시 소탕이 가능합니다.",
            "&f&l[소탕(우클릭)] ${cashingMessage(4)}",
            "",
            "&c&l2분 안에 선택 해야 합니다! &a&l빠르게 선택 할수록 추가 점수가 지급됩니다.(최대 60,000 점수)",
        ), true)

        gui.setItem(23, getItem(
            "emerald",
            "&8&l[&a&l보상(Extreme)&8&l]",
            listOf(
                "&f&l난이도 완료 점수&8&l\\&&f&l보상: &d&l450,000 점수&8&l, &b&l4 캐시&8&l(30분 생존 시 지급)",
                "&f&l5분 주기 점수: &d&l50,000 점수",
                "&e&l몬스터 처치 시간 점수: &d&l최대 240,000 점수 &8&l(스캘레톤 처치 시 지급, 10ms마다 4점 감소)",
            )
        ))

        gui.setItem(32, getItem(
            "book",
            "&8&l[&c&l패널티(Extreme)&8&l]",
            listOf(
                "&f&l허기 V",
                "&f&l구속 II",
                "&f&l채굴 피로 I",
                "&f&l5분 마다 스캘레톤 소환/체력 200칸/신속 I/화염 저항 I/5분 안에 못 죽일 시 5분 주기 점수 0.5배로 지급",
                "&8&l - 방어구: 보호 II 다이아 풀/힘 III, 밀어내기 II 활",
                "&c&l나약함 II",
            )
        ))

        fi(15, "mace", "&d&l얼티밋(Ultimate)", listOf(
            "&f&l[선택(좌클릭)] &f&l클리어 시 소탕이 가능합니다.",
            "&f&l[소탕(우클릭)] ${cashingMessage(5)}",
            "",
            "&c&l2분 안에 선택 해야 합니다! &a&l빠르게 선택 할수록 추가 점수가 지급됩니다.(최대 60,000 점수)",
        ), true)

        gui.setItem(24, getItem(
            "emerald",
            "&8&l[&a&l보상(Ultimate)&8&l]",
            listOf(
                "&f&l난이도 완료 점수&8&l\\&&f&l보상: &d&l550,000 점수&8&l, &b&l6 캐시&8&l(30분 생존 시 지급)",
                "&f&l5분 주기 점수: &d&l60,000 점수",
                "&f&l몬스터 처치 시간 점수: &d&l최대 240,000 점수 &8&l(스캘레톤 처치 시 지급, 10ms마다 4점 감소)",
                "&a&l2차 몬스터 처치 시간 점수: &d&l최대 360,000 점수 &8&l(좀비 처치 시 지급, 10ms마다 8점 감소)",
            )
        ))

        gui.setItem(33, getItem(
            "book",
            "&8&l[&c&l패널티(Ultimate)&8&l]",
            listOf(
                "&f&l허기 V",
                "&f&l구속 II",
                "&f&l채굴 피로 I",
                "&f&l5분 마다 스캘레톤 소환/체력 200칸/신속 I/화염 저항 I/5분 안에 못 죽일 시 5분 주기 점수 0.5배로 지급",
                "&8&l - 방어구: 보호 II 다이아 풀/힘 III, 밀어내기 II 활",
                "&f&l나약함 II",
                "&c&l시듦 IV",
                "&c&l5분 마다 좀비 소환/체력 350칸/신속 I/화염 저항 I/힘 I/5분 안에 못 죽일 시 체력 9칸 깎임",
                "&8&l - 방어구: 보호 II 다이아 풀/날카로움 III 다이아 검",
            )
        ))

        fi(40, "barrier", "&7&l선택안함(Nothing)", listOf(
            "",
            "&8&l선택 횟수는 증가 안합니다."
        ))

        player.openInventory(gui)
        player.playSound(player.location, Sound.AMBIENT_CAVE, sound, 1f)
    }
}