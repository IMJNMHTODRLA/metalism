package _RedGold__.main.command.betting.sys.lottoGui

import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.api.toFormat
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player

class LottoGui {
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
        val gui = LottoHolder().inventory

        val background = getItem(
            "magenta_stained_glass_pane",
            prefix
        )

        for (i in 0 until gui.size) gui.setItem(i, background)

        val itemNumList = listOf(19, 20, 21, 23, 24, 25)

        for (i in 0..5) {
            gui.setItem(itemNumList[i], getItem(
                "gold_nugget",
                "&f&l[ &e&l1 &f&l]",
                listOf("", "&7클릭 시 ${i + 1}번째 로또 번호가 1 증가됩니다.", "&7현재 고른 로또 번호: [1, 1, 1, 1, 1, 1]")
            ).apply {amount = 1})
        }

        gui.setItem(22, getItem(
            "emerald",
            "&b&l추첨 시작하기",
            listOf(
                "",
                "&7&l시작 시 &f&l5,000 &7&l골드가 소비 됨과 동시에 추첨이 시작됩니다.",
                "",
                "&6&l1등 &7&l- &f&l100,000,000 골드&7&l(6자리)",
                "${rgb("FFCC00")}&l2등 &7&l- &f&l50,000,000 골드&7&l(5자리)",
                "&e&l3등 &7&l- &f&l10,000,000 골드&7&l(4자리)",
                "&f&l4등 &7&l- &f&l1,000,000 골드&7&l(3자리)",
                "&7&l5등 &7&l- &f&l10,000 골드&7&l(2자리)",
                "&7&l6등 &7&l- &f&l5,000 골드&7&l(1자리)",
                "&8&l낙첨.. &7&l- &f&l0 골드&7&l(0자리)",
            )
        ).apply {addUnsafeEnchantment(Enchantment.LUCK_OF_THE_SEA, 5)})

        player.openInventory(gui)
        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
    }
}