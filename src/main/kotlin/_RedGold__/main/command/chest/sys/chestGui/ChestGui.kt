package _RedGold__.main.command.chest.sys.chestGui

import _RedGold__.main.function.api.isFileExists
import _RedGold__.main.function.api.readGzipByte
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.util.io.BukkitObjectInputStream
import java.io.ByteArrayInputStream

class ChestGui(private val plugin: JavaPlugin) {
    fun openGui(player: Player) {
        val gui = ChestHolder(false).inventory
        val uuid = player.uniqueId

        val strUuid = uuid.toString().replace("-", "")
        val pathStr = "chest/${strUuid.substring(0, 2)}/$strUuid/"

        Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
            val itemList: MutableList<ItemStack?> = mutableListOf()

            for (i in 0 until gui.size) {
                if (!isFileExists(plugin, pathStr, "$i.dat.gz")) {
                    itemList.add(i, null)
                    continue
                }

                val gzipRead = readGzipByte(plugin, pathStr, "$i.dat.gz")

                val item = BukkitObjectInputStream(ByteArrayInputStream(gzipRead)).use { ii ->
                    ii.readObject() as ItemStack
                }

                itemList.add(i, item)
            }

            Bukkit.getScheduler().runTask(plugin, Runnable {
                for (i in 0 until itemList.size) {
                    if (itemList[i] == null) continue
                    gui.setItem(i, itemList[i])
                }

                if (player.isOnline) {
                    (gui.holder as ChestHolder).isOpenChest = true

                    player.openInventory(gui)
                    player.playSound(player.location, Sound.BLOCK_CHEST_OPEN, 1f, 1f)
                }
            })
        })
    }
}