package _RedGold__.main.command.betting.sys.highLow

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.api.toFormat
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player

class HighLowGui {
    private val prefix = """
        ${rgb("2444FC")}§l[
        ${rgb("2A48FC")}§lM
        ${rgb("304CFC")}§lE
        ${rgb("3651FD")}§lT
        ${rgb("3C55FD")}§lA
        ${rgb("4359FD")}§lL
        ${rgb("495DFD")}§lI
        ${rgb("4F61FD")}§lS
        ${rgb("5565FD")}§lM 
        ${rgb("616EFE")}§lB
        ${rgb("6772FE")}§lE
        ${rgb("6D76FE")}§lT
        ${rgb("747AFE")}§lT
        ${rgb("7A7EFE")}§lI
        ${rgb("8083FF")}§lN
        ${rgb("8687FF")}§lG
        ${rgb("8C8BFF")}§l]
    """.trimIndent().replace("\n", "")

    fun openGui(player: Player) {
        val gui = HighLowHolder().inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            prefix
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        gui.setItem(29, getItem(
            "redstone",
            "&c&l50 미만",
            listOf("", "&7클릭 시 50 미만으로 선택 됩니다.")
        ))

        gui.setItem(31, getItem(
            "chiseled_stone_bricks",
            "&7&l정확히 50",
            listOf("", "&7클릭 시 50으로 선택 됩니다.")
        ))

        gui.setItem(33, getItem(
            "emerald",
            "&a&l50 초과",
            listOf("", "&7&l클릭 시 50 초과로 선택 됩니다.")
        ))

        gui.setItem(22, getItem(
            "black_concrete",
            "&7&l도박 시작하기",
            listOf("", "&7&l도박 시작할려면 클릭해주세요.")
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
            "&6&l배팅 금액&f: ${(gui.holder as HighLowHolder).betGold.toFormat()} &6&l골드",
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