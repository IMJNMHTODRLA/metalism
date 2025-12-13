package _RedGold__.main.command.shop.sys.goldShop.mineralGui

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
class MineralListener(private val plugin: JavaPlugin) : Listener {
    private fun buy(player: Player, id: String, name: String, removeGold: Int, itemNumber: Long) {
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

    private fun sell(player: Player, id: String, name: String, addGold: Int, itemNumber: Long) {
        val material = valueOf(id.replace("minecraft:", "").uppercase(Locale.getDefault()))
        val target = ItemStack(material)

        if (player.inventory.containsAtLeast(target, itemNumber.toInt())) {
            for (i in 0 until itemNumber) player.inventory.removeItem(target)

            player.sendMessage(gc("&a${(addGold * itemNumber).toFormat()}골드를 얻었습니다."))
            val gold = getData(plugin, player, "gold").toLong()
            saveData(plugin, player, "gold", gold + (addGold * itemNumber))
            addMakeGold(plugin, addGold * itemNumber)

            player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
        } else {
            player.sendMessage(gc("&c${name}이(가) 부족합니다."))
            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is MineralHolder) {
            val player = event.whoClicked as Player
            val clickType = event.click
            val slot = event.slot
            val holder = event.inventory.holder as MineralHolder
            val sellVal = holder.sell
            val purVal = holder.pur
            event.isCancelled = true

            if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
                val itemNumber = if (clickType == ClickType.SHIFT_LEFT) 64L else 1L

                when (slot) {
                    10 -> buy(player, "coal", "석탄", purVal[0], itemNumber)
                    11 -> buy(player, "raw_copper", "구리 원석", purVal[1], itemNumber)
                    12 -> buy(player, "copper_ingot", "구리 주괴", purVal[2], itemNumber)
                    13 -> buy(player, "raw_iron", "철 원석", purVal[3], itemNumber)
                    14 -> buy(player, "iron_ingot", "철 주괴", purVal[4], itemNumber)
                    15 -> buy(player, "raw_gold", "금 원석", purVal[5], itemNumber)
                    16 -> buy(player, "gold_ingot", "금 주괴", purVal[6], itemNumber)

                    19 -> buy(player, "lapis_lazuli", "청금석", purVal[7], itemNumber)
                    20 -> buy(player, "redstone", "레드스톤", purVal[8], itemNumber)
                    21 -> buy(player, "diamond", "다이아몬드", purVal[9], itemNumber)
                    22 -> buy(player, "emerald", "에메랄드", purVal[10], itemNumber)
                    23 -> buy(player, "netherite_scrap", "네더라이트 파편", purVal[11], itemNumber)
                    24 -> buy(player, "netherite_ingot", "네더라이트 주괴", purVal[12], itemNumber)
                    25 -> buy(player, "quartz", "석영", purVal[13], itemNumber)
                }
                return
            } else if (clickType == ClickType.RIGHT || clickType == ClickType.SHIFT_RIGHT) {
                val itemNumber = if (clickType == ClickType.SHIFT_RIGHT) 64L else 1L

                when (slot) {
                    10 -> sell(player, "coal", "석탄", sellVal[0], itemNumber)
                    11 -> sell(player, "raw_copper", "구리 원석", sellVal[1], itemNumber)
                    12 -> sell(player, "copper_ingot", "구리 주괴", sellVal[2], itemNumber)
                    13 -> sell(player, "raw_iron", "철 원석", sellVal[3], itemNumber)
                    14 -> sell(player, "iron_ingot", "철 주괴", sellVal[4], itemNumber)
                    15 -> sell(player, "raw_gold", "금 원석", sellVal[5], itemNumber)
                    16 -> sell(player, "gold_ingot", "금 주괴", sellVal[6], itemNumber)

                    19 -> sell(player, "lapis_lazuli", "청금석", sellVal[7], itemNumber)
                    20 -> sell(player, "redstone", "레드스톤", sellVal[8], itemNumber)
                    21 -> sell(player, "diamond", "다이아몬드", sellVal[9], itemNumber)
                    22 -> sell(player, "emerald", "에메랄드", sellVal[10], itemNumber)
                    23 -> sell(player, "netherite_scrap", "네더라이트 파편", sellVal[11], itemNumber)
                    24 -> sell(player, "netherite_ingot", "네더라이트 주괴", sellVal[12], itemNumber)
                    25 -> sell(player, "quartz", "석영", sellVal[13], itemNumber)
                }
                return
            }
        }
    }
}