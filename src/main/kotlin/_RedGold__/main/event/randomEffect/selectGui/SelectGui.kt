package _RedGold__.main.event.randomEffect.selectGui

import _RedGold__.main.Main.Event.EVENT_ITEM
import _RedGold__.main.Main.Event.EVENT_NAME
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.max
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.point
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

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
            gui.setItem(n, getItem(
                item,
                "$title 선택 횟수: (${max[uuid]}/3)",
                change
            ).apply {if (isEnchant) addUnsafeEnchantment(Enchantment.SHARPNESS, 8)})
        }

        fi(11, "iron_sword", "&e&l보통(Normal)", listOf(
            "",
            "&f&l난이도 완료 점수: &d&l100,000 점수&8&l(30분 생존 시 지급)",
            "&f&l난이도 완료 보상: &2&l10 토큰, &a&l0 고급 토큰&8&l(30분 생존 시 지급)",
            "&f&l5분 주기 점수: &d&l10,000 점수",
            "",
            "&8&l[&c&l패널티&8&l]",
            "&c&l허기 V",
            "",
            "&c&l2분 안에 선택 해야 합니다!",
            "&a&l빠르게 선택 할수록 추가 점수가 지급됩니다.(최대 60,000 점수)",
            "",
            "&e&l체감 난이도: &f&l0.5&8&l/&c&l10.0(투표 불가)"
        ))

        fi(12, "diamond_sword", "&c&l어려움(Hard)", listOf(
            "",
            "&f&l난이도 완료 점수: &d&l150,000 점수&8&l(30분 생존 시 지급)",
            "&f&l난이도 완료 보상: &2&l20 토큰, &a&l0 고급 토큰&8&l(30분 생존 시 지급)",
            "&f&l5분 주기 점수: &d&l15,000 점수",
            "",
            "&8&l[&c&l패널티&8&l]",
            "&f&l허기 V",
            "&c&l구속 II",
            "",
            "&c&l2분 안에 선택 해야 합니다!",
            "&a&l빠르게 선택 할수록 추가 점수가 지급됩니다.(최대 60,000 점수)",
            "",
            "&e&l체감 난이도: &f&l1.0&8&l/&c&l10.0(5명 투표)"
        ))

        fi(13, "netherite_sword", "&4&l하드코어(HardCore)", listOf(
            "",
            "&f&l난이도 완료 점수: &d&l300,000 점수&8&l(30분 생존 시 지급)",
            "&f&l난이도 완료 보상: &2&l40 토큰, &a&l20 고급 토큰&8&l(30분 생존 시 지급)",
            "&f&l5분 주기 점수: &d&l30,000 점수",
            "",
            "&8&l[&c&l패널티&8&l]",
            "&f&l허기 V",
            "&f&l구속 II",
            "&c&l채굴 피로 I",
            "&c&l5분 마다 스캘레톤 소환/체력 200칸/신속 I/화염 저항 I/5분 안에 못 죽일 시 5분 주기 점수 0.5배로 지급",
            "",
            "&c&l2분 안에 선택 해야 합니다!",
            "&a&l빠르게 선택 할수록 추가 점수가 지급됩니다.(최대 60,000 점수)",
            "",
            "&e&l체감 난이도: &e&l3.0&8&l/&c&l10.0(5명 투표)"
        ))

        fi(14, "netherite_axe", "&b&l익스트림(Extreme)", listOf(
            "",
            "&f&l난이도 완료 점수: &d&l350,000 점수&8&l(30분 생존 시 지급)",
            "&f&l난이도 완료 보상: &2&l50 토큰, &a&l25 고급 토큰&8&l(30분 생존 시 지급)",
            "&f&l5분 주기 점수: &d&l35,000 점수",
            "",
            "&8&l[&c&l패널티&8&l]",
            "&f&l허기 V",
            "&f&l구속 II",
            "&f&l채굴 피로 I",
            "&f&l5분 마다 스캘레톤 소환/체력 200칸/신속 I/화염 저항 I/5분 안에 못 죽일 시 5분 주기 점수 0.5배로 지급",
            "&c&l나약함 II",
            "",
            "&c&l2분 안에 선택 해야 합니다!",
            "&a&l빠르게 선택 할수록 추가 점수가 지급됩니다.(최대 60,000 점수)",
            "",
            "&e&l체감 난이도: &c&l5.0&8&l/&c&l10.0(5명 투표)"
        ), true)

        fi(15, "mace", "&d&l얼티밋(Ultimate)", listOf(
            "",
            "&f&l난이도 완료 점수: &d&l550,000 점수&8&l(30분 생존 시 지급)",
            "&f&l난이도 완료 보상: &2&l60 토큰, &a&l30 고급 토큰&8&l(30분 생존 시 지급)",
            "&f&l5분 주기 점수: &d&l60,000 점수",
            "",
            "&8&l[&c&l패널티&8&l]",
            "&f&l허기 V",
            "&f&l구속 II",
            "&f&l채굴 피로 I",
            "&f&l나약함 II",
            "&f&l5분 마다 스캘레톤 소환/체력 200칸/신속 I/화염 저항 I/5분 안에 못 죽일 시 5분 주기 점수 0.5배로 지급",
            "&c&l시듦 IV",
            "&c&l5분 마다 좀비 소환/체력 350칸/신속 I/화염 저항 I/힘 II/5분 안에 못 죽일 시 체력 9칸 깎임",
            "",
            "&c&l2분 안에 선택 해야 합니다!",
            "&a&l빠르게 선택 할수록 추가 점수가 지급됩니다.(최대 60,000 점수)",
            "",
            "&e&l체감 난이도: &4&l8.8&8&l/&c&l10.0(5명 투표)"
        ), true)

        fi(22, "barrier", "&7&l선택안함(Nothing)", listOf(
            "",
            "&8&l선택 횟수는 증가 안합니다."
        ))

        player.openInventory(gui)
        player.playSound(player.location, Sound.AMBIENT_CAVE, 1f, 1f)
    }
}