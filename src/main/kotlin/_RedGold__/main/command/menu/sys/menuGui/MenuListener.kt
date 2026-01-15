package _RedGold__.main.command.menu.sys.menuGui

import _RedGold__.main.command.shop.sys.dailyShop.dailyGui.DailyGui
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.ServerGold.addMakeGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class MenuListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is MenuHolder) {
            val player = event.whoClicked as Player
            val slot = event.slot
            val holder = event.inventory.holder as MenuHolder
            val clickType = event.click
            event.isCancelled = true

            when (slot) {
                10 -> player.performCommand("rtp")
                11 -> player.performCommand("shop")
                12 -> player.performCommand("ranking")
                13 -> player.performCommand("chest")
                14 -> player.performCommand("boost")
                15 -> player.performCommand("betting")
                16 -> player.performCommand("home")

                19 -> player.performCommand("back")
                20 -> player.performCommand("event")
                21 -> player.performCommand("mission daily")
                22 -> player.performCommand("style")
                23 -> player.performCommand("ec")
                24 -> player.performCommand("discord")

                45 -> {
                    if (clickType == ClickType.LEFT) {
                        if (holder.cashExc >= 15) {
                            player.sendMessage(gc("&c더 이상 환전을 할 수 없습니다. 다음 주에 환전해주세요."))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                            return
                        }

                        val gold = getData(plugin, player, "gold").toLong()

                        if (gold < 100_000) {
                            player.sendMessage(gc("&c골드가 부족합니다. 필요 골드: ${(100_000 - gold).toFormat()}골드"))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                            return
                        }
                        val cash = getData(plugin, player, "cash").toLong()

                        player.sendMessage(gc("&a10 캐시로 환전하였습니다."))

                        saveData(plugin, player, "cash", cash + 10)
                        saveData(plugin, player, "gold", gold - 100_000)
                        saveData(plugin, player, "cash_exc", holder.cashExc + 1)

                        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 2f)
                        MenuGui(plugin).openGui(player, 0f)
                        return
                    }

                    if (clickType == ClickType.RIGHT) {
                        val cash = getData(plugin, player, "cash").toLong()

                        if (cash < 1) {
                            player.sendMessage(gc("&c캐시가 부족합니다. 필요 캐시: 1캐시"))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                            return
                        }

                        val gold = getData(plugin, player, "gold").toLong()

                        player.sendMessage(gc("&a9,500 골드로 환전하였습니다."))

                        saveData(plugin, player, "cash", cash - 1)
                        saveData(plugin, player, "gold", gold + 9500)
                        addHoldGold(plugin, 500)

                        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 2f)
                        MenuGui(plugin).openGui(player, 0f)
                        return
                    }
                }
            }
        }
    }
}