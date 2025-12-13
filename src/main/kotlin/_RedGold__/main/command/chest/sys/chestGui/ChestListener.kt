package _RedGold__.main.command.chest.sys.chestGui

import _RedGold__.main.function.api.deleteFile
import _RedGold__.main.function.api.isFileExists
import _RedGold__.main.function.api.saveGzipByte
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Item
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
    private fun itemToByte(item: ItemStack): ByteArray {
        val byteOut = ByteArrayOutputStream()

        BukkitObjectOutputStream(byteOut).use { out ->
            out.writeObject(item)
        }

        return byteOut.toByteArray()
    }

    @EventHandler
    fun onCloseInventory(event: InventoryCloseEvent) {
        if (event.inventory.holder is ChestHolder) {
            val player = event.player as Player

            val uuid = player.uniqueId
            val gui = event.inventory
            val strUuid = uuid.toString().replace("-", "")

            Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
                if ((gui.holder as ChestHolder).isOpenChest) {
                    for (i in 0 until gui.size) {
                        val item = gui.getItem(i)

                        if (item != null) saveGzipByte(
                            plugin, "chest/${strUuid.substring(0, 2)}/$strUuid/", "$i.dat.gz", itemToByte(item)
                        ) else if (isFileExists(plugin, "chest/${strUuid.substring(0, 2)}/$strUuid/", "$i.dat.gz")) deleteFile(
                            plugin, "chest/${strUuid.substring(0, 2)}/$strUuid/", "$i.dat.gz"
                        )
                    }
                }

                Bukkit.getScheduler().runTask(plugin, Runnable {
                    player.playSound(player.location, Sound.BLOCK_CHEST_CLOSE, 1f, 1f)
                })
            })
        }
    }
}