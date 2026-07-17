package _RedGold__.main.commands.user.shop.listeners._cashShop고쳐야함_.styleGui

import _RedGold__.main.Main.Gacha.GACHA_POINT_TO_GOLD_TIMES
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.sys.Chat.ChatApply.MAX_STYLE
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireJavaPlugin
@RequireListener
class StyleListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is StyleHolder) {
            val player = event.whoClicked as Player
            val holder = event.inventory.holder as StyleHolder
            val clickType = event.click
            val slot = event.slot
            val isBuy = holder.isBuy
            event.isCancelled = true

            val purVal: List<Long> = listOf(
                28, 44, 24, 28, 16, 16, 20,
                24, 16, 24, 24, 26, 26, 26
            )

            fun buy(id: Int) {
                if (isBuy[id]) {
                    _RedGold__.main.commands.user.cosmetic.listeners.cosmeticGui.CosmeticGui(plugin).openGui(player)
                    return
                }

                val ticketPoint = getData(plugin, player, "ticket/point").toInt()

                if (ticketPoint < purVal[id]) {
                    player.fail("&c뽑기 포인트가 부족합니다. 필요 뽑기 포인트: ${(purVal[id] - ticketPoint).toFormat()} 뽑기 포인트")
                    return
                }

                saveData(plugin, player, "ticket/point", ticketPoint - purVal[id])
                saveData(plugin, player, "style/${MAX_STYLE + id}", 1)

                addHoldGold(plugin, ticketPoint * GACHA_POINT_TO_GOLD_TIMES)
                player.good("&a칭호 구매가 완료되었습니다.")
                StyleGui(plugin).openGui(player, 0f)
            }

            if (clickType == ClickType.LEFT) {
                when (slot) {
                    10 -> buy(0)
                    11 -> buy(1)
                    12 -> buy(2)
                    13 -> buy(3)
                    14 -> buy(4)
                    15 -> buy(5)
                    16 -> buy(6)

                    19 -> buy(7)
                    20 -> buy(8)
                    21 -> buy(9)
                    22 -> buy(10)
                    23 -> buy(11)
                    24 -> buy(12)
                    25 -> buy(13)
                }
                return
            }
        }
    }
}