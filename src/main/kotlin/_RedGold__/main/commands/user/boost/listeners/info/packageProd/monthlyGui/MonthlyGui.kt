package _RedGold__.main.commands.user.boost.listeners.info.packageProd.monthlyGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.toPercent
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class MonthlyGui {
    fun openGui(player: Player) {
        val gui = MonthlyHolder().inventory
        gui.item(BACKGROUND)

        gui.item[12] = getItem(
            Material.DIAMOND,
            "&e&l클릭하여 월간 패키지 구매하기",
            listOf("",
                "&f&l구매가: &4&l${MonthlyConst.PRICE.toFormat()} 루비",
                "",
                "&c&o* 계정당 마지막 구매 이후 30일 마다",
                "&c&o* 최대 1회 구매 가능합니다."
            )
        )

        gui.item[14] = getItem(
            Material.BOOK,
            "&e&l혜택 정보",
            listOf("",
                "&a&l[ 구매 시 혜택 ]",
                "&7&l  - &b&l${MonthlyConst.pack.giveCrystal.toFormat()} 크리스탈 지급",
                "",
                "&b&l[ 구매 직후 30일 간 혜택 ]",
                "&7&l  - &e&l일일 접속 시 ${MonthlyConst.pack.dailyCrystal.toFormat()} 크리스탈 지급",
                "&7&l  - &e&l일일 접속 시 무작위 일반 강화석 지급",
                "&7&l  - &e&l획득하는 모든 경험치 &f&l${MonthlyConst.pack.multipleExp.toPercent} &e&l배수 적용",
            )
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_NOTE_BLOCK_BASS)
    }
}