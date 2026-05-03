package _RedGold__.main.commands.user.boost.listeners.info.rankProd.rankProdGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class RankProdGui {
    fun openGui(player: Player) {
        val gui = RankProdHolder().inventory
        gui.item(BACKGROUND)

        gui.item[12] = getItem(
            Material.GOLD_INGOT,
            "${PermissionEnum.VIP.prefix} 랭크",
            listOf("", "&7&l클릭하여 혜택을 볼 수 있습니다.")
        )

        gui.item[14] = getItem(
            Material.EMERALD,
            "${PermissionEnum.MVP.prefix} 랭크",
            listOf("", "&7&l클릭하여 혜택을 볼 수 있습니다.")
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_NOTE_BLOCK_BASS)
    }
}