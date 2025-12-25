package _RedGold__.main.command.betting.sys.coinGui

import _RedGold__.main.command.chest.sys.chestGui.ChestHolder
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.api.toFormat
import org.bukkit.Sound
import org.bukkit.entity.Player

class CoinGui {
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
        val gui = CoinHolder().inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            prefix
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        gui.setItem(20, getItem(
            "emerald",
            "&a앞면",
            listOf("", "&7클릭 시 앞면으로 선택 됨과 동시에 도박이 시작 됩니다.")
        ))

        gui.setItem(22, getItem(
            "black_concrete",
            "&8알 수 없음",
            listOf("", "&c도박이 시작되지 않았습니다.")
        ))

        gui.setItem(24, getItem(
            "redstone",
            "&c뒷면",
            listOf("", "&7클릭 시 뒷면으로 선택 됨과 동시에 도박이 시작 됩니다.")
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
            "&6&l배팅 금액&f: ${(gui.holder as CoinHolder).betGold.toFormat()} &6&l골드",
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