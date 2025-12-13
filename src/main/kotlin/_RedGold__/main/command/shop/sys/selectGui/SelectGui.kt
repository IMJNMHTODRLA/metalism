package _RedGold__.main.command.shop.sys.selectGui

import _RedGold__.main.command.menu.sys.menuGui.MenuHolder
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.defData
import _RedGold__.main.function.Gui.getItem
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class SelectGui {
    fun openGui(player: Player) {
        val gui = SelectHolder().inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            """
                ${rgb("2444FC")}§l§o[
                ${rgb("2947FC")}§l§oM
                ${rgb("2E4BFC")}§l§oE
                ${rgb("334EFC")}§l§oT
                ${rgb("3852FD")}§l§oA
                ${rgb("3D55FD")}§l§oL
                ${rgb("4258FD")}§l§oI
                ${rgb("475CFD")}§l§oS
                ${rgb("4C5FFD")}§l§oM 
                ${rgb("5666FD")}§l§oS
                ${rgb("5A69FE")}§l§oH
                ${rgb("5F6DFE")}§l§oO
                ${rgb("6470FE")}§l§oP 
                ${rgb("6E77FE")}§l§oS
                ${rgb("737AFE")}§l§oE
                ${rgb("787DFE")}§l§oL
                ${rgb("7D81FF")}§l§oE
                ${rgb("8284FF")}§l§oC
                ${rgb("8788FF")}§l§oT
                ${rgb("8C8BFF")}§l§o]
            """.trimIndent().replace("\n", "")
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        gui.setItem(11, getItem(
            "gold_ingot",
            "&6&l골드 상점"
        ))

        gui.setItem(12, getItem(
            "player_head",
            "&a&l유저 상점"
        ))

        gui.setItem(13, getItem(
            "emerald",
            "&b&l캐시 상점"
        ))

        gui.setItem(14, getItem(
            "experience_bottle",
            "&e&l일일 상점"
        ))

        gui.setItem(15, getItem(
            "enchanted_golden_apple",
            "&b&l월간 상점"
        ))

        player.openInventory(gui)
        player.playSound(player.location, Sound.BLOCK_CHEST_OPEN, 1f, 1f)
    }
}