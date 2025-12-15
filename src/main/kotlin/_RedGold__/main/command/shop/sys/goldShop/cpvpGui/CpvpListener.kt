package _RedGold__.main.command.shop.sys.goldShop.cpvpGui

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.ServerGold.addHoldGold
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

@RequireListener
@RequireJavaPlugin
class CpvpListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is CpvpHolder) {
            val player = event.whoClicked as Player
            val clickType = event.click
            val slot = event.slot
            event.isCancelled = true

            val purVal: List<Long> = listOf(4000, 2000, 6000, 3000, 2500, 3500, 1500, 2500, 1500)

            if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
                val itemNumber = if (clickType == ClickType.SHIFT_LEFT) 64L else 1L

                fun buy(id: String, name: String, purId: Int) {
                    val material = valueOf(id.replace("minecraft:", "").uppercase(Locale.getDefault()))
                    val target = ItemStack(material)
                    val gold = getData(plugin, player, "gold").toLong()

                    if (gold >= purVal[purId] * itemNumber) {
                        for (i in 0 until itemNumber) player.inventory.addItem(target)

                        player.sendMessage(gc("&a${name}을(를) ${itemNumber}개 구매했습니다."))
                        saveData(plugin, player, "gold", gold - (purVal[purId] * itemNumber))
                        addHoldGold(plugin, purVal[purId] * itemNumber)

                        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                    } else {
                        player.sendMessage(gc("&c골드가 부족합니다. 필요 골드: ${(purVal[purId] * itemNumber - gold).toFormat()}골드"))
                        player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                    }
                }

                when (slot) {
                    10 -> buy("end_crystal", "엔드 수정", 0)
                    11 -> buy("obsidian", "흑요석", 1)
                    12 -> buy("totem_of_undying", "불사의 토템", 2)
                    13 -> buy("golden_apple", "황금 사과", 3)
                    14 -> buy("ender_pearl", "엔더 진주", 4)
                    15 -> buy("respawn_anchor", "리스폰 정박기", 5)
                    16 -> buy("glowstone", "발광석", 6)

                    19 -> buy("experience_bottle", "경험치 병", 7)
                    20 -> buy("arrow", "화살", 8)
                }
            }
        }
    }
}