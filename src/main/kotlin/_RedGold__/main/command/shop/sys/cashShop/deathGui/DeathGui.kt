package _RedGold__.main.command.shop.sys.cashShop.deathGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.api.toFormat
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin

class DeathGui(private val plugin: JavaPlugin) {
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
        val gui = DeathHolder().inventory
        val deathSound = getData(plugin, player, "death_sound").toInt()

        fun Int.fi(title: String, pur: Int, type: Int) {
            val typeMsg = if (deathSound == type) " &a&l[선택됨]" else ""

            gui.setItem(this, getItem(
                "writable_book",
                "&f&l$title$typeMsg",
                listOf(
                    "&f",
                    prefix,
                    "&a&l[구매(좌클릭)] &f&l구매가: ${pur.toFormat()}캐시",
                    "&a&l[미리듣기(우클릭)] &7&l우클릭 시 소리를 들을 수 있습니다.",
                    "&f",
                    "&c&l새 사망 사운드를 구매하면 기존 사망 사운드를 재구매 해야 합니다."
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
                    "&c&l새 사망 사운드를 구매하면 기존 사망 사운드를 재구매 해야 합니다."
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
        11.fi("물에 빠진", 400, 1) //minecraft:entity.player.splash 1.0
        12.fi("귀신", 400, 2) //minecraft:ambient.cave 1.0
        13.fi("비", 400, 3) //minecraft:weather.rain 1.0
        14.fi("흑우", 450, 4) //minecraft:entity.cow.death 1.0
        15.fi("박쥐", 450, 5) //minecraft:entity.bat.death 1.0
        16.fi("돼지", 450, 6) //minecraft:entity.pig.death 1.0

        19.fi("모루", 450, 7) //minecraft:block.anvil.land 1.0
        20.fi("부숴진", 500, 8) //minecraft:item.totem.use 2.0
        21.fi("폭팔", 550, 9) //minecraft:entity.generic.explode
        22.fi("먹다", 400, 10) //minecraft:entity.generic.eat
        23.fi("타버림", 400, 11) //minecraft:entity.generic.extinguish_fire
        24.fi("금고 부숨", 500, 12) //minecraft:block.vault.break
        25.fi("웅장한 브금(김)", 800, 13) //minecraft:music.credits

        player.openInventory(gui)
        player.playSound(player.location, Sound.UI_BUTTON_CLICK, sound, 1f)
    }
}