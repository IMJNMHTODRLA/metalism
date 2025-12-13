package _RedGold__.main.command.betting.sys.diceGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.api.toFormat
import org.bukkit.Sound
import org.bukkit.entity.Player

class DiceGui {
    fun openGui(player: Player) {
        val gui = DiceHolder().inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            """
                ${rgb("2444FC")}§l§o[
                ${rgb("2947FC")}§l§oM
                ${rgb("2D4AFC")}§l§oE
                ${rgb("324EFC")}§l§oT
                ${rgb("3751FD")}§l§oA
                ${rgb("3C54FD")}§l§oL
                ${rgb("4057FD")}§l§oI
                ${rgb("455BFD")}§l§oS
                ${rgb("4A5EFD")}§l§oM 
                ${rgb("5364FD")}§l§oD
                ${rgb("5868FE")}§l§oI
                ${rgb("5D6BFE")}§l§oC
                ${rgb("616EFE")}§l§oE 
                ${rgb("6B74FE")}§l§oB
                ${rgb("7078FE")}§l§oE
                ${rgb("747BFE")}§l§oT
                ${rgb("797EFE")}§l§oT
                ${rgb("7E81FF")}§l§oI
                ${rgb("8385FF")}§l§oN
                ${rgb("8788FF")}§l§oG
                ${rgb("8C8BFF")}§l§o]
            """.trimIndent().replace("\n", "")
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        val itemNumList = listOf(19, 20, 21, 23, 24, 25)

        for (i in 1..6) {
            gui.setItem(itemNumList[i - 1], getItem(
                "gold_nugget",
                "&e${i}번",
                listOf("", "&7클릭 시 ${i}번으로 선택 됩니다.")
            ).apply {amount = i})
        }

        gui.setItem(22, getItem(
            "emerald",
            "&7&l주사위 굴리기",
            listOf("", "&7&l주사위를 굴릴려면 클릭해주세요.")
        ))



        gui.setItem(45, getItem(
            "red_stained_glass_pane",
            "&c-100,000 골드",
            listOf("", "&7클릭 시 베팅 금액에서 100,000 골드가 회수됩니다.")
        ))

        gui.setItem(46, getItem(
            "red_stained_glass_pane",
            "&c-10,000 골드",
            listOf("", "&7클릭 시 베팅 금액에서 10,000 골드가 회수됩니다.")
        ))

        gui.setItem(47, getItem(
            "red_stained_glass_pane",
            "&c-1,000 골드",
            listOf("", "&7클릭 시 베팅 금액에서 1,000 골드가 회수됩니다.")
        ))

        gui.setItem(48, getItem(
            "red_stained_glass_pane",
            "&c-100 골드",
            listOf("", "&7클릭 시 베팅 금액에서 100 골드가 회수됩니다.")
        ))



        gui.setItem(49, getItem(
            "gray_stained_glass_pane",
            "&6&l배팅 금액&f: ${(gui.holder as DiceHolder).betGold.toFormat()} &6&l골드",
        ))



        gui.setItem(50, getItem(
            "green_stained_glass_pane",
            "&a+100 골드",
            listOf("", "&7클릭 시 배팅 금액에 100 골드를 추가합니다.")
        ))

        gui.setItem(51, getItem(
            "green_stained_glass_pane",
            "&a+1,000 골드",
            listOf("", "&7클릭 시 배팅 금액에 1,000 골드를 추가합니다.")
        ))

        gui.setItem(52, getItem(
            "green_stained_glass_pane",
            "&a+10,000 골드",
            listOf("", "&7클릭 시 배팅 금액에 10,000 골드를 추가합니다.")
        ))

        gui.setItem(53, getItem(
            "green_stained_glass_pane",
            "&a+100,000 골드",
            listOf("", "&7클릭 시 배팅 금액에 100,000 골드를 추가합니다.")
        ))

        player.openInventory(gui)
        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
    }
}