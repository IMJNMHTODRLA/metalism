package _RedGold__.main.commands.user.boost.listeners.info.packageProd.starterGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class StarterGui {
    fun openGui(player: Player) {
        val gui = StarterHolder().inventory
        gui.item(BACKGROUND)

        gui.item[12] = getItem(
            Material.GOLD_INGOT,
            "&e&l클릭하여 스타터 패키지 구매하기",
            listOf("",
                "&f&l구매가: &4&l${StarterConst.PRICE.toFormat()} 루비",
                "",
                "&c&o* 계정당 최대 1회 구매입니다."
            )
        )

        gui.item[14] = getItem(
            Material.BOOK,
            "&e&l혜택 정보",
            listOf("",
                "&a&l[ 구매 시 혜택 ]",
                "&7&l  - &6&l${StarterConst.pack.giveGold.toFormat()} 골드 지급",
                "&7&l  - &b&l${StarterConst.pack.giveCrystal.toFormat()} 크리스탈 지급",
                "&7&l  - &e&l그 외 각종 유용한 아이템 지급",
            )
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_NOTE_BLOCK_BASS)
    }
}