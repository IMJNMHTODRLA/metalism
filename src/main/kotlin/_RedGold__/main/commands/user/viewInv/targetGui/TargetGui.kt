package _RedGold__.main.commands.user.viewInv.targetGui

import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Sound
import org.bukkit.entity.Player

class TargetGui {
    fun openGui(player: Player, target: Player) {
        val gui = TargetHolder(target.name).inventory
        gui.item(BACKGROUND)

        val storageContents = target.inventory.storageContents
        val armorContents = target.inventory.armorContents
        val itemInOffHand = target.inventory.itemInOffHand

        storageContents.forEachIndexed { i, item ->
            gui.item[i] = item
        }

        armorContents.forEachIndexed { i, item ->
            gui.item[45 + i] = item
        }

        gui.item[53] = itemInOffHand

        player.inv + gui
        player.sendSound(Sound.BLOCK_NOTE_BLOCK_PLING)
    }
}