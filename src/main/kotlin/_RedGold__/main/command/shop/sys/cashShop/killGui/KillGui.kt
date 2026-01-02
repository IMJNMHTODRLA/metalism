package _RedGold__.main.command.shop.sys.cashShop.killGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.api.toFormat
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class KillGui(private val plugin: JavaPlugin) {
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
        val gui = KillHolder().inventory
        val killSound = getData(plugin, player, "kill_sound").toInt()

        fun Int.fi(title: String, pur: Int, type: Int) {
            val typeMsg = if (killSound == type) " &a&l[선택됨]" else ""

            gui.setItem(this, getItem(
                "writable_book",
                "&f&l$title$typeMsg",
                listOf(
                    "&f",
                    prefix,
                    "&a&l[구매(좌클릭)] &f&l구매가: ${pur.toFormat()}캐시",
                    "&a&l[미리듣기(우클릭)] &7&l우클릭 시 소리를 들을 수 있습니다.",
                    "&f",
                    "&c&l새 킬 사운드를 구매하면 기존 킬 사운드를 재구매 해야 합니다."
                )
            ))
        }

        fun Int.none() {
            gui.setItem(this, getItem(
                "barrier",
                "&c&l판매하고 있는 아이템이 아닙니다.",
                listOf(
                    "&f",
                    prefix,
                    "&c&l[구매 불가]",
                    "&c&l[미리듣기 불가]",
                    "&f",
                    "&c&l새 킬 사운드를 구매하면 기존 킬 사운드를 재구매 해야 합니다."
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
        for (i in 27 until gui.size) gui.setItem(i, background2)

        gui.setItem(31, getItem(
            "book",
            "&8&l현재 페이지: (1/1)",
        ))

        10.fi("선택 안함", 0, 0) //
        11.fi("철퇴", 650, 1) //item.mace.smash_ground_heavy 1.0
        12.fi("꿀", 600, 2) //minecraft:block.honey_block.fall 1.0
        13.fi("슬라임", 600, 3) //minecraft:block.slime_block.break 1.0
        14.fi("좀비", 600, 4) //minecraft:entity.zombie.death 1.0
        15.fi("철문 공격", 650, 5) //minecraft:entity.zombie.attack_iron_door 1.0
        16.fi("조글린", 650, 6) //minecraft:entity.zoglin.death 1.0

        19.fi("모루", 650, 7) //minecraft:block.anvil.use 1.0
        20.fi("발전 과제", 700, 8) //minecraft:ui.toast.challenge_complete 1.0
        21.fi("셜커", 650, 9) //minecraft:entity.shulker.ambient
        22.fi("염소뿔", 950, 10) //minecraft:item.goat_horn.sound.1
        23.fi("삼지창", 800, 11) //minecraft:item.trident.riptide_1
        24.fi("웅장한 삼지창", 900, 12) //minecraft:item.trident.thunder
        25.fi("마심", 700, 13) //minecraft:entity.generic.drink

        player.openInventory(gui)
        player.playSound(player.location, Sound.UI_BUTTON_CLICK, sound, 1f)
    }
}