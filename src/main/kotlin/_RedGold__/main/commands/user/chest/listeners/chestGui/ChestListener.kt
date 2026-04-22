package _RedGold__.main.commands.user.chest.listeners.chestGui

import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.functions.Scheduler.taskAsync
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.chestManager.DEF_CHEST_SLOT
import _RedGold__.main.managers.chestManager.saveChest
import _RedGold__.main.managers.playerData.data
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.ByteArrayOutputStream

@RequireListener
@RequireJavaPlugin
class ChestListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onCloseInventory(event: InventoryCloseEvent) {
        val gui = event.inventory
        if (gui.holder !is ChestHolder) return

        val player = event.player as Player
        val uuid = player.uniqueId
        val page = (gui.holder as ChestHolder).page

        plugin.taskAsync {
            saveChest(uuid, page * (DEF_CHEST_SLOT + 1), gui.contents)

            plugin.task {
                player.sendSound(Sound.BLOCK_CHEST_CLOSE)
            }
        }
    }
}