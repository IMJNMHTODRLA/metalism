package _RedGold__.main.commands.user.boost.listeners.info.crystalProd.limitGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.PlusMath.pow
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class LimitGui {
    fun openGui(player: Player) {
        val gui = LimitHolder().inventory
        gui.item(BACKGROUND)

        val round = LimitConst.nowRound(player)

        gui.item[12] = getItem(
            Material.DIAMOND_BLOCK,
            "&e&l클릭하여 한정 판매 크리스탈 구매하기",
            listOf("",
                "&f&l구매가: &4&l${LimitConst.price[round].toFormat()} 루비",
                "&f&l다음 회차: &e&l${round + 1}회차",
                "",
                "&c&o* 계정당 마지막 구매 이후 30일 마다",
                "&c&o* 최대 5회 구매 가능합니다."
            )
        )

        gui.item[14] = getItem(
            Material.BOOK,
            "&e&l혜택 정보",
            listOf("",
                "&a&l[ 구매 시 혜택 ]",
                "&7&l  - &b&l1회차 구매: ${LimitConst.pack.giveCrystal[0].toFormat()} 크리스탈 지급",
                "&7&l  - &b&l2회차 구매: ${LimitConst.pack.giveCrystal[1].toFormat()} 크리스탈 지급",
                "&7&l  - &b&l3회차 구매: ${LimitConst.pack.giveCrystal[2].toFormat()} 크리스탈 지급",
                "&7&l  - &b&l4회차 구매: ${LimitConst.pack.giveCrystal[3].toFormat()} 크리스탈 지급",
                "&7&l  - &b&l5회차 구매: ${LimitConst.pack.giveCrystal[4].toFormat()} 크리스탈 지급",
            )
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_NOTE_BLOCK_BASS)
    }
}