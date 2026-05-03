package _RedGold__.main.commands.user.boost.listeners.settings.settingsGui

import _RedGold__.main.functions.Color.rgb
import _RedGold__.main.functions.Gui.getItem
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player

class SelectGui {
    fun openGui(player: Player) {
        val gui = SelectHolder().inventory

        val background = getItem(
            "magenta_stained_glass_pane",
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
                ${rgb("6973FE")}§l§oB
                ${rgb("7078FE")}§l§oO
                ${rgb("777DFE")}§l§oO
                ${rgb("7E82FF")}§l§oS
                ${rgb("8586FF")}§l§oT
                ${rgb("8C8BFF")}§l§o]
            """.trimIndent().replace("\n", "")
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        gui.setItem(4, getItem(
            "paper",
            "&6&lGG 색깔 바꾸기"
        ).apply {addUnsafeEnchantment(Enchantment.SHARPNESS, 5)})

        gui.setItem(12, getItem(
            "netherite_sword",
            "&c&l킬 메시지 변경"
        ).apply {addUnsafeEnchantment(Enchantment.SHARPNESS, 5)})

        gui.setItem(13, getItem(
            "redstone",
            "&4&l사망 메시지 변경"
        ))

        gui.setItem(14, getItem(
            "gunpowder",
            "&c&l퇴장 메시지 변경"
        ))

        gui.setItem(22, getItem(
            "barrier",
            "&7&l기타 기능\\&설정"
        ))

        player.openInventory(gui)
        player.playSound(player.location, Sound.BLOCK_CHEST_OPEN, 1f, 1f)
    }
}