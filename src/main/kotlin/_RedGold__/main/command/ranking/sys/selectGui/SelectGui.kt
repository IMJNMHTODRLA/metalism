package _RedGold__.main.command.ranking.sys.selectGui

import _RedGold__.main.command.menu.sys.menuGui.MenuHolder
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.defData
import _RedGold__.main.function.Gui.getItem
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class SelectGui {
    fun openGui(player: Player) {
        val gui = SelectHolder().inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            """
                ${rgb("2444FC")}§l§o[
                ${rgb("2847FC")}§l§oM
                ${rgb("2D4AFC")}§l§oE
                ${rgb("314DFC")}§l§oT
                ${rgb("3550FD")}§l§oA
                ${rgb("3A53FD")}§l§oL
                ${rgb("3E56FD")}§l§oI
                ${rgb("4259FD")}§l§oS
                ${rgb("475CFD")}§l§oM 
                ${rgb("4F62FD")}§l§oR
                ${rgb("5465FD")}§l§oA
                ${rgb("5868FE")}§l§oN
                ${rgb("5C6AFE")}§l§oK
                ${rgb("616DFE")}§l§oI
                ${rgb("6570FE")}§l§oN
                ${rgb("6973FE")}§l§oG 
                ${rgb("7279FE")}§l§oS
                ${rgb("767CFE")}§l§oE
                ${rgb("7B7FFF")}§l§oL
                ${rgb("7F82FF")}§l§oE
                ${rgb("8385FF")}§l§oC
                ${rgb("8888FF")}§l§oT
                ${rgb("8C8BFF")}§l§o]
            """.trimIndent().replace("\n", "")
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        gui.setItem(3, getItem(
            "gold_ingot",
            "&6&l골드 순위"
        ))

        gui.setItem(4, getItem(
            "emerald",
            "&b&l캐시 순위"
        ).apply {addUnsafeEnchantment(Enchantment.SHARPNESS, 5)})

        gui.setItem(5, getItem(
            "experience_bottle",
            "&2&l플레이 타임 순위"
        ).apply {addUnsafeEnchantment(Enchantment.LUCK_OF_THE_SEA, 5)})

        gui.setItem(13, getItem(
            "emerald_block",
            "&a&l후원 순위"
        ).apply {addUnsafeEnchantment(Enchantment.SHARPNESS, 5)})

        gui.setItem(21, getItem(
            "end_crystal",
            "&d&l킬 순위"
        ).apply {addUnsafeEnchantment(Enchantment.SHARPNESS, 5)})

        gui.setItem(22, getItem(
            "redstone",
            "&4&l사망 순위"
        ).apply {addUnsafeEnchantment(Enchantment.LUCK_OF_THE_SEA, 5)})

        gui.setItem(23, getItem(
            "diamond_sword",
            "&c&l연킬 순위",
            listOf("", "&8&l연킬 보상 획득이 가능합니다.")
        ).apply {addUnsafeEnchantment(Enchantment.SHARPNESS, 5)})

        player.openInventory(gui)
        player.playSound(player.location, Sound.BLOCK_CHEST_OPEN, 1f, 1f)
    }
}