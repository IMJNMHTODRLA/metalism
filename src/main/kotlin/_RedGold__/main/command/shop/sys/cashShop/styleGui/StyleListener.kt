package _RedGold__.main.command.shop.sys.cashShop.styleGui

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import _RedGold__.main.sys.Chat.ChatApply.MAX_STYLE
import org.bukkit.Material.ENCHANTED_BOOK
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta
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
                1400, 2200, 1200, 1400, 800, 800, 1000,
                1200, 800, 1200, 1200, 1300, 1300, 1300
            )

            fun buy(id: Int) {
                if (isBuy[id]) {
                    _RedGold__.main.command.style.sys.StyleGui(plugin).openGui(player)
                    return
                }

                val cash = getData(plugin, player, "cash").toLong()

                if (cash < purVal[id]) {
                    player.sendMessage(gc("&c캐시가 부족합니다. 필요 캐시: ${(purVal[id] - cash).toFormat()}캐시"))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                    return
                }

                saveData(plugin, player, "cash", cash - purVal[id])
                saveData(plugin, player, "style/${MAX_STYLE + id}", 1)
                addHoldGold(plugin, purVal[id] * 10_000)

                player.sendMessage(gc("&a칭호 구매가 완료되었습니다."))

                player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
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