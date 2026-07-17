package _RedGold__.main.commands.user.betting.listeners.lottoGui

import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.modify
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class LottoGui {
    fun openGui(player: Player) {
        val gui = LottoHolder().inventory
        gui.item(BACKGROUND)

        repeat(6) { i ->
            gui.item[
                if (i in 0..2) 19 + i else 20 + i
            ] = getItem(
                Material.GOLD_INGOT,
                "&f&l[ &e&l1 &f&l]",
                listOf("", "&7클릭 시 ${i + 1}번째 로또 번호가 1 증가됩니다.", "&7현재 고른 로또 번호: [1, 1, 1, 1, 1, 1]")
            ).apply {amount = 1}
        }

        gui.item[22] = getItem(
            Material.EMERALD,
            "&b&l추첨 시작하기",
            listOf(
                "",
                "&7&l클릭 시 &f&l${LottoConst.AMOUNT} &7&l골드가 소비 됨과 동시에 추첨이 시작됩니다.",
                "&7&lSHIFT + 클릭 시 로또 번호가 자동으로 선택 되며",
                "&f&l1,000 &7&l골드가 소비 됨과 동시에 추첨이 시작됩니다.",
                "",
                "&b&l1등 &7&l- &f&l125,000,000 골드&7&l(6자리)",
                "&6&l2등 &7&l- &f&l2,500,000 골드&7&l(5자리)",
                "&e&l3등 &7&l- &f&l100,000 골드&7&l(4자리)",
                "&f&l4등 &7&l- &f&l5,000 골드&7&l(3자리)",
                "&f&l5등 &7&l- &f&l2,500 골드&7&l(2자리)",
                "&8&l낙첨... &7&l- &f&l0 골드&7&l(0~1자리)",
            )
        ).modify { enchantEffect() }

        player.openInventory(gui)
        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
    }
}