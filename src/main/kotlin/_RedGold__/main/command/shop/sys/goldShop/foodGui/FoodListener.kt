package _RedGold__.main.command.shop.sys.goldShop.foodGui

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
class FoodListener(private val plugin: JavaPlugin) : Listener {
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

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is FoodHolder) {
            val player = event.whoClicked as Player
            val holder = event.inventory.holder as FoodHolder
            val clickType = event.click
            val slot = event.slot
            val page = holder.page
            event.isCancelled = true

            val purVal: List<Long> = listOf(
                800, 800, 800, 2500, 800, 1500, 1500,
                1500, 1500, 1500, 1500, 1500, 1500, 1500,
                2000, 1500, 1500, 1500, 1500
            )

            if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
                val itemNumber = if (clickType == ClickType.SHIFT_LEFT) 64L else 1L

                if (page == 1) {
                    when (slot) {
                        10 -> buy(player, "sweet_berries", "달콤한 열매", purVal[0], itemNumber)
                        11 -> buy(player, "glow_berries", "발광 열매", purVal[1], itemNumber)
                        12 -> buy(player, "chorus_fruit", "후렴과", purVal[2], itemNumber)
                        13 -> buy(player, "golden_carrot", "황금 당근", purVal[3], itemNumber)
                        14 -> buy(player, "baked_potato", "구운 감자", purVal[4], itemNumber)
                        15 -> buy(player, "bread", "빵", purVal[5], itemNumber)
                        16 -> buy(player, "cooked_beef", "스테이크", purVal[6], itemNumber)

                        19 -> buy(player, "cooked_porkchop", "익힌 돼지고기", purVal[7], itemNumber)
                        20 -> buy(player, "cooked_mutton", "익힌 양고기", purVal[8], itemNumber)
                        21 -> buy(player, "cooked_chicken", "익힌 닭고기", purVal[9], itemNumber)
                        22 -> buy(player, "cooked_rabbit", "익힌 토끼고기", purVal[10], itemNumber)
                        23 -> buy(player, "cooked_cod", "익힌 대구", purVal[11], itemNumber)
                        24 -> buy(player, "cooked_salmon", "익힌 연어", purVal[12], itemNumber)
                        25 -> buy(player, "cookie", "쿠키", purVal[13], itemNumber)

                        35 -> FoodGui().openGui(player, page + 1)
                    }
                    return
                } else {
                    when (slot) {
                        10 -> buy(player, "cake", "케이크", purVal[14], itemNumber)
                        11 -> buy(player, "pumpkin_pie", "호박 파이", purVal[15], itemNumber)
                        12 -> buy(player, "mushroom_stew", "버섯 스튜", purVal[16], itemNumber)
                        13 -> buy(player, "beetroot_soup", "비트 스튜", purVal[17], itemNumber)
                        14 -> buy(player, "rabbit_stew", "토끼 스튜", purVal[18], itemNumber)

                        27 -> FoodGui().openGui(player, page - 1)
                    }
                    return
                }
            }
        }
    }
}