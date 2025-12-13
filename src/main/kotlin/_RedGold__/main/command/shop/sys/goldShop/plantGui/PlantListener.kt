package _RedGold__.main.command.shop.sys.goldShop.plantGui

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.ServerGold.addMakeGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import org.bukkit.Material.*
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

@RequireJavaPlugin
@RequireListener
class PlantListener(private val plugin: JavaPlugin) : Listener {
    private fun buy(player: Player, id: String, name: String, removeGold: Long, itemNumber: Long) {
        val material = valueOf(id.replace("minecraft:", "").uppercase(Locale.getDefault()))
        val target = ItemStack(material)
        val gold = getData(plugin, player, "gold").toLong()

        if (gold >= removeGold * itemNumber) {
            for (i in 0 until itemNumber) player.inventory.addItem(target)

            player.sendMessage(gc("&a${name}을(를) ${itemNumber}개 구매했습니다."))
            saveData(plugin, player, "gold", gold - (removeGold * itemNumber))
            addHoldGold(plugin, removeGold * itemNumber)

            player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
        } else {
            player.sendMessage(gc("&c골드가 부족합니다. 필요 골드: ${(removeGold * itemNumber - gold).toFormat()}골드"))
            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
        }
    }

    private fun sell(player: Player, id: String, name: String, addGold: Long, itemNumber: Long, sellId: Int, sellTimes: Int, sellMax: Int = 256) {
        val material = valueOf(id.replace("minecraft:", "").uppercase(Locale.getDefault()))
        val target = ItemStack(material)

        if (sellTimes + itemNumber > sellMax) {
            player.sendMessage(gc("&c더 이상 판매를 할 수 없습니다. 다음 주에 판매해주세요."))
            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
            return
        }

        if (player.inventory.containsAtLeast(target, itemNumber.toInt())) {
            for (i in 0 until itemNumber) player.inventory.removeItem(target)

            player.sendMessage(gc("&a${(addGold * itemNumber).toFormat()}골드를 얻었습니다."))
            val gold = getData(plugin, player, "gold").toLong()

            saveData(plugin, player, "gold", gold + (addGold * itemNumber))
            saveData(plugin, player, "plant_shop/$sellId", sellTimes + itemNumber)
            addMakeGold(plugin, addGold * itemNumber)

            player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
            PlantGui(plugin).openGui(player)
        } else {
            player.sendMessage(gc("&c${name}이(가) 부족합니다."))
            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is PlantHolder) {
            val player = event.whoClicked as Player
            val clickType = event.click
            val slot = event.slot
            val holder = event.inventory.holder as PlantHolder
            event.isCancelled = true

            if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
                val itemNumber = if (clickType == ClickType.SHIFT_LEFT) 64L else 1L

                when (slot) {
                    11 -> buy(player, "wheat_seeds", "밀 씨앗", 150, itemNumber)
                    13 -> buy(player, "beetroot_seeds", "비트 씨앗", 150, itemNumber)
                    14 -> buy(player, "potato", "감자", 700, itemNumber)
                    16 -> buy(player, "carrot", "당근", 750, itemNumber)

                    19 -> buy(player, "nether_wart", "네더 사마귀", 1000, itemNumber)
                    21 -> buy(player, "pumpkin_seeds", "호박씨", 150, itemNumber)
                    23 -> buy(player, "melon_seeds", "수박씨", 150, itemNumber)
                    24 -> buy(player, "cocoa_beans", "&f&l코코아 콩", 600, itemNumber)
                    25 -> buy(player, "sugar_cane", "&f&l사탕수수", 900, itemNumber)
                }
                return
            } else if (clickType == ClickType.RIGHT || clickType == ClickType.SHIFT_RIGHT) {
                val itemNumber = if (clickType == ClickType.SHIFT_RIGHT) 64L else 1L

                when (slot) {
                    10 -> sell(player, "wheat", "밀", 800, itemNumber, 0, holder.sellTimes[0])
                    11 -> sell(player, "wheat_seeds", "밀 씨앗", 100, itemNumber, 1, holder.sellTimes[1], 4096)
                    12 -> sell(player, "beetroot", "비트", 800, itemNumber, 2, holder.sellTimes[2])
                    13 -> sell(player, "beetroot_seeds", "비트 씨앗", 100, itemNumber, 3, holder.sellTimes[3], 4096)
                    14 -> sell(player, "potato", "감자", 600, itemNumber, 4, holder.sellTimes[4])
                    15 -> sell(player, "poisonous_potato", "독이 든 감자", 6500, itemNumber, 5, holder.sellTimes[5], 16)
                    16 -> sell(player, "carrot", "당근", 750, itemNumber, 6, holder.sellTimes[6])

                    19 -> sell(player, "nether_wart", "네더 사마귀", 900, itemNumber, 7, holder.sellTimes[7])
                    20 -> sell(player, "pumpkin", "호박", 700, itemNumber, 8, holder.sellTimes[8])
                    21 -> sell(player, "pumpkin_seeds", "호박씨", 100, itemNumber, 9, holder.sellTimes[9], 4096)
                    22 -> sell(player, "melon_slice", "수박 조각", 500, itemNumber, 10, holder.sellTimes[10], 256)
                    23 -> sell(player, "melon_seeds", "수박씨", 100, itemNumber, 11, holder.sellTimes[11], 4096)
                    24 -> sell(player, "cocoa_beans", "&f&l코코아 콩", 500, itemNumber, 12, holder.sellTimes[12])
                    25 -> sell(player, "sugar_cane", "&f&l사탕수수", 800, itemNumber, 13, holder.sellTimes[13])
                }
                return
            }
        }
    }
}