package _RedGold__.main.commands.user.boost.listeners.settings.settingsGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class SettingsGui {
    fun openGui(player: Player) {
        val gui = SettingsHolder().inventory
        gui.item(BACKGROUND)

        gui.item[12] = getItem(
            Material.BOOK,
            "&a&lVIP 기능 설정"
        )

        gui.item[14] = getItem(
            Material.BOOK,
            "&6&lMVP 기능 설정"
        )

        player.inv + gui
        player.sendSound(Sound.BLOCK_CHEST_OPEN)
    }
}