package _RedGold__.main.commands.user.boost.listeners.info.packageProd.packageProdGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class PackageProdGui {
    fun openGui(player: Player) {
        val gui = PackageProdHolder().inventory
        gui.item(BACKGROUND)

        gui.item[12] = getItem(
            Material.GOLD_INGOT,
            "&e&l스타터 패키지",
            listOf("", "&7&l클릭하여 혜택을 볼 수 있습니다.")
        )

        gui.item[14] = getItem(
            Material.EMERALD,
            "&b&l월간 패키지",
            listOf("", "&7&l클릭하여 혜택을 볼 수 있습니다.")
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_NOTE_BLOCK_BASS)
    }
}