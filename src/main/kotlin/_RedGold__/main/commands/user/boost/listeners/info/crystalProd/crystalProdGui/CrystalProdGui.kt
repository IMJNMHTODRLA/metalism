package _RedGold__.main.commands.user.boost.listeners.info.crystalProd.crystalProdGui

import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.modify
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class CrystalProdGui {
    fun openGui(player: Player) {
        val gui = CrystalProdHolder().inventory
        gui.item(BACKGROUND)

        gui.item[12] = getItem(
            Material.DIAMOND_BLOCK,
            "&b&l한정 판매 크리스탈",
            listOf("", "&7&l클릭하여 혜택을 볼 수 있습니다.")
        ).modify { enchantEffect() }

        gui.item[14] = getItem(
            Material.DIAMOND,
            "&e&l상시 판매 크리스탈",
            listOf("", "&7&l클릭하여 혜택을 볼 수 있습니다.")
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_NOTE_BLOCK_BASS)
    }
}