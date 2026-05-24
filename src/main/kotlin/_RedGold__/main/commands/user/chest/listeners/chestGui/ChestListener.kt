package _RedGold__.main.commands.user.chest.listeners.chestGui

import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.task
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.chestManager.DEF_CHEST_SLOT
import _RedGold__.main.managers.chestManager.saveChest
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent

@RequireListener
class ChestListener : Listener {
    @EventHandler
    fun onCloseInventory(event: InventoryCloseEvent) {
        val gui = event.inventory
        if (gui.holder !is ChestHolder) return

        val player = event.player as Player
        val uuid = player.uniqueId
        val page = (gui.holder as ChestHolder).page

        taskAsync {
            saveChest(uuid, page * (DEF_CHEST_SLOT + 1), gui.contents)

            task {
                player.sendSound(Sound.BLOCK_CHEST_CLOSE)
            }
        }
    }
}