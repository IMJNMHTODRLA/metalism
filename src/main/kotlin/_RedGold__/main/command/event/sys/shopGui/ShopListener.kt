package _RedGold__.main.command.event.sys.shopGui

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
class ShopListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is ShopHolder) {
            val player = event.whoClicked as Player
            val clickType = event.click
            val slot = event.slot
            event.isCancelled = true

            if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
                fun remove(name: String, removeToken: Long? = null, removeAdvanced: Long? = null): Boolean {
                    var isSuss = false

                    if (removeToken != null) {
                        val token = getData(plugin, player, "token/normal").toLong()

                        if (token >= removeToken) {
                            player.sendMessage(gc("&a${name}을(를) 구매했습니다."))
                            saveData(plugin, player, "token/normal", token - removeToken)
                            addHoldGold(plugin, removeToken * 10000)

                            player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                            isSuss = true
                        } else {
                            player.sendMessage(gc("&c토큰이 부족합니다. 필요 토큰: ${(removeToken - token).toFormat()} 토큰"))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                        }
                    }

                    if (removeAdvanced != null) {
                        val token = getData(plugin, player, "token/advanced").toLong()

                        if (token >= removeAdvanced) {
                            player.sendMessage(gc("&a${name}을(를) 구매했습니다."))
                            saveData(plugin, player, "token/advanced", token - removeAdvanced)
                            addHoldGold(plugin, removeAdvanced * 50000)

                            player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                            isSuss = true
                        } else {
                            player.sendMessage(gc("&c고급 토큰이 부족합니다. 필요 토큰: ${(removeAdvanced - token).toFormat()} 고급 토큰"))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                        }
                    }

                    return isSuss
                }

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