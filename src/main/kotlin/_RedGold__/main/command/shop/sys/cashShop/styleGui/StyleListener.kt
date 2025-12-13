package _RedGold__.main.command.shop.sys.cashShop.styleGui

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
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

            fun buy(id: Int, removeCash: Long) {
                if (isBuy[id]) {
                    _RedGold__.main.command.style.sys.StyleGui(plugin).openGui(player)
                    return
                }

                val cash = getData(plugin, player, "cash").toLong()

                if (cash < removeCash) {
                    player.sendMessage(gc("&c캐시가 부족합니다. 필요 캐시: ${(removeCash - cash).toFormat()}캐시"))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                    return
                }

                saveData(plugin, player, "cash", cash - removeCash)
                saveData(plugin, player, "style/$id", 1)
                addHoldGold(plugin, removeCash * 10_000)

                player.sendMessage(gc("&a칭호 구매가 완료되었습니다."))

                player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                StyleGui(plugin).openGui(player, 0f)
            }

            if (clickType == ClickType.LEFT) {
                when (slot) {
                    10 -> buy(5, 1200)
                    11 -> buy(6, 2000)
                    12 -> buy(7, 1000)
                    13 -> buy(8, 1200)
                    14 -> buy(9, 600)
                    15 -> buy(10, 600)
                    16 -> buy(11, 800)
                }
                return
            }
        }
    }
}