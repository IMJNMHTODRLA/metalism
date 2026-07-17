package _RedGold__.main.commands.user.boost.listeners.info.crystalProd.alwaysGui

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

class AlwaysGui {
    fun openGui(player: Player) {
        val gui = AlwaysHolder().inventory
        val holder = gui.holder as AlwaysHolder

        gui.item(BACKGROUND)

        repeat(4) { i ->
            val crystal = AlwaysConst.DEFAULT_CRYSTAL * 10.pow(i)

            gui.item[21 - i] = getItem(
                Material.RED_STAINED_GLASS_PANE,
                "&c-${crystal.toFormat()} 크리스탈",
            )

            gui.item[23 + i] = getItem(
                Material.GREEN_STAINED_GLASS_PANE,
                "&a+${crystal.toFormat()} 크리스탈",
            )
        }

        gui.item[22] = getItem(
            Material.GRAY_STAINED_GLASS_PANE,
            "&b&l구매 할 크리스탈&f: &b&l${holder.addCrystal.toFormat()} 크리스탈"
        )

        gui.item[13] = getItem(
            Material.DIAMOND,
            "&e&l클릭하여 상시 판매 크리스탈 구매하기",
            "",
            "&b&l구매 할 크리스탈&f: &b&l${holder.addCrystal.toFormat()} 크리스탈",
            "&f&l구매가: &4&l${holder.getTotalPrice.toFormat()} 루비",
            "",
            "&8&o* 구매 제한 없음"
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_NOTE_BLOCK_BASS)
    }
}