package _RedGold__.main.commands.user.home.homeGui

import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.Color.sendTitleMsg
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.isNull
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.OVER_WORLD
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.dataManager.HomeData
import _RedGold__.main.managers.playerData.dataManager.LocationData
import _RedGold__.main.managers.playerData.variableManager.RANGE_HOME
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class HomeListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is HomeHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val clickType = event.click
        val slot = event.slot

        val i = HomeConst.getIndex(slot)
        if (i !in RANGE_HOME) return
        val homeData = player.data.homeMap[i]?: HomeData()

        when(clickType) {
            ClickType.DROP -> {
                if (!homeData.isUnlocked && homeData.location.isNull()) return

                player.data.homeMap[i]?.let { it.location = null }
                player.good("&a지정된 홈 삭제가 완료되었습니다.")
                HomeGui().openGui(player)
                return
            }

            ClickType.LEFT, ClickType.RIGHT -> {
                if (!homeData.isUnlocked) {
                    val buyAmount = HomeConst.buyAmount(i)
                    if (player.data.gold < buyAmount) {
                        player.fail("&c골드가 부족합니다. 필요 골드: ${(buyAmount - player.data.gold).toFormat()} 골드")
                        return
                    }

                    player.data.gold -= buyAmount
                    player.data.homeMap[i]?.let { it.isUnlocked = true }

                    player.good("&a${i + 1}번 홈을 구매하였습니다.")
                    HomeGui().openGui(player)
                    return
                }

                val location = homeData.location
                if (location == null) {
                    if (player.world.name != OVER_WORLD) {
                        player.fail("&c오버월드에서만 위치 지정이 가능합니다.")
                        return
                    }

                    player.data.homeMap[i]?.let { it.location = LocationData(player.location) }
                    player.good("&a${i + 1}번 홈 위치를 지정하였습니다.")
                    HomeGui().openGui(player)
                    return
                }

                player.teleportAsync(location())

                player.sendMsg("&a${i + 1}번 홈으로 이동했습니다.")
                player.sendTitleMsg("&a${i + 1}번 홈으로 이동했습니다.")
                player.sendSound(Sound.ENTITY_ENDERMAN_TELEPORT)
                return
            }

            else -> return
        }
    }
}