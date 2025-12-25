package _RedGold__.main.command.betting.sys.selectGui

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
                ${rgb("2847FC")}§l§oM
                ${rgb("2D4AFC")}§l§oE
                ${rgb("314DFC")}§l§oT
                ${rgb("3550FD")}§l§oA
                ${rgb("3A53FD")}§l§oL
                ${rgb("3E56FD")}§l§oI
                ${rgb("4259FD")}§l§oS
                ${rgb("475CFD")}§l§oM 
                ${rgb("4F62FD")}§l§oB
                ${rgb("5465FD")}§l§oE
                ${rgb("5868FE")}§l§oT
                ${rgb("5C6AFE")}§l§oT
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

        gui.setItem(10, getItem(
            "gold_ingot",
            "&6&l동전 던지기"
        ))

        gui.setItem(12, getItem(
            "observer",
            "&7&l주사위 굴리기"
        ))

        gui.setItem(14, getItem(
            "experience_bottle",
            "&b&lHIGH&8&l\\&&c&lLOW"
        ))

        gui.setItem(16, getItem(
            "diamond",
            "&e&l로또 추첨"
        ))

        player.openInventory(gui)
        player.playSound(player.location, Sound.BLOCK_CHEST_OPEN, 1f, 1f)
    }
}