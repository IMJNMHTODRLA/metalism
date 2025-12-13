package _RedGold__.main.command.home.sys.home

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class HomeListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is HomeHolder) {
            val player = event.whoClicked as Player
            val slot = event.slot
            val holder = event.inventory.holder as HomeHolder
            event.isCancelled = true

            if (slot !in 3..5) return

            val slotToHome = slot - 3
            val buyHome = holder.buyHome[slotToHome]
            val saveHome = holder.saveHome[slotToHome]
            val homePrice = holder.homePrice[slotToHome]
            val clickType = event.click

            if (buyHome == 0) {
                val gold = getData(plugin, player, "gold").toLong()

                if (gold - homePrice < 0) {
                    player.sendMessage(gc("&c골드가 부족합니다. 필요 골드: ${(homePrice - gold).toFormat()}"))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                    return
                }

                saveData(plugin, player, "gold", gold - homePrice.toLong())
                addHoldGold(plugin, homePrice.toLong())

                saveData(plugin, player, "home/buy/$slotToHome", 1)

                player.sendMessage(gc("&a${slotToHome + 1}번 홈이 구매가 완료 되었습니다."))
                player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)

                HomeGui(plugin).openGui(player)
            } else if (saveHome == "n;n;n") {
                if (player.world.name != "world") {
                    player.sendMessage(gc("&c오버월드에서만 지정이 가능합니다."))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                    return
                }

                saveData(plugin, player, "home/save/$slotToHome", "${player.x};${player.y};${player.z}")

                player.sendMessage(gc("&a${slotToHome + 1}번 홈을 지정했습니다."))
                player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)

                HomeGui(plugin).openGui(player)
            } else {
                if (clickType == ClickType.DROP) {
                    if (player.world.name != "world") {
                        player.sendMessage(gc("&c오버월드에서만 지정이 가능합니다."))
                        player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                        return
                    }

                    saveData(plugin, player, "home/save/$slotToHome", "${player.x};${player.y};${player.z}")

                    player.sendMessage(gc("&a${slotToHome + 1}번 홈을 지정했습니다."))
                    player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)

                    HomeGui(plugin).openGui(player)
                    return
                }

                val savedHome = getData(plugin, player, "home/save/$slotToHome").split(";")
                val x = savedHome[0].toDouble()
                val y = savedHome[1].toDouble()
                val z = savedHome[2].toDouble()

                player.closeInventory()

                player.teleport(Location(Bukkit.getWorld("world"), x, y, z))
                player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f)
                player.sendMessage(gc("&a${slotToHome + 1}번 홈으로 이동했습니다."))
            }
        }
    }
}