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
        if (event.inventory.holder is CpvpHolder) {
            val player = event.whoClicked as Player
            val clickType = event.click
            val slot = event.slot
            event.isCancelled = true

            val purVal = listOf(4000, 2000, 6000, 3000, 2500, 3500, 1500, 2500, 1500)

            if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
                val itemNumber = if (clickType == ClickType.SHIFT_LEFT) 64L else 1L

                when (slot) {
                    10 -> buy(player, "end_crystal", "엔드 수정", 6000, itemNumber)
                    11 -> buy(player, "obsidian", "흑요석", 4000, itemNumber)
                    12 -> buy(player, "totem_of_undying", "불사의 토템", 8000, itemNumber)
                    13 -> buy(player, "golden_apple", "황금 사과", 5000, itemNumber)
                    14 -> buy(player, "ender_pearl", "엔더 진주", 5000, itemNumber)
                    15 -> buy(player, "respawn_anchor", "리스폰 정박기", 6000, itemNumber)
                    16 -> buy(player, "glowstone", "발광석", 4000, itemNumber)

                    19 -> buy(player, "experience_bottle", "경험치 병", 3000, itemNumber)
                    20 -> buy(player, "arrow", "화살", 2000, itemNumber)
                }
            }
        }
    }
}