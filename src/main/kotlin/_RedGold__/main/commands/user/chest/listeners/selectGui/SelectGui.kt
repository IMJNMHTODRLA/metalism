package _RedGold__.main.commands.user.chest.listeners.selectGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class SelectGui {
    fun openGui(player: Player) {
        val gui = SelectHolder().inventory
        gui.item(BACKGROUND)

        repeat(5) { i ->
            gui.item[11 + i] = getItem(
                Material.CHEST,
                "&e&l${i}번 창고"
            )
        }

        player.openInventory(gui)
        player.sendSound(Sound.UI_BUTTON_CLICK)
    }
}