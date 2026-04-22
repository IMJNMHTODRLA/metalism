package _RedGold__.main.commands.user.home.homeGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.isNull
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.dataManager.HomeData
import _RedGold__.main.managers.playerData.variableManager.TOTAL_HOME
import org.bukkit.Sound
import org.bukkit.entity.Player

class HomeGui {
    fun openGui(player: Player) {
        val gui = HomeHolder().inventory
        gui.item(BACKGROUND)

        repeat(TOTAL_HOME) { i ->
            val slot = HomeConst.getSlot(i)
            val data = player.data.homeMap[i]?: HomeData()

            if (!data.isUnlocked) {
                gui.item[slot] = HomeConst.getNotBuyHome(i)
                return@repeat
            }

            if (data.location.isNull()) {
                gui.item[slot] = HomeConst.getNotSetHome(i)
                return@repeat
            }

            gui.item[slot] = HomeConst.getSetHome(i, data)
        }

        player.inv + gui
        player.sendSound(Sound.BLOCK_NOTE_BLOCK_BASS)
    }
}