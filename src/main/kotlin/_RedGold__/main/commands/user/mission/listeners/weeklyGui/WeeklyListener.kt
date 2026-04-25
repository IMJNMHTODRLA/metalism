package _RedGold__.main.commands.user.mission.listeners.weeklyGui

import _RedGold__.main.commands.user.mission.listeners.achievementGui.AchievementGui
import _RedGold__.main.commands.user.mission.listeners.dailyGui.DailyGui
import _RedGold__.main.functions.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.ServerGold.addMakeGold
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin


@RequireJavaPlugin
@RequireListener
class WeeklyListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is WeeklyHolder) {
            val player = event.whoClicked as Player
            val holder = event.inventory.holder as WeeklyHolder
            val slot = event.slot

            val progressList = holder.progressList
            val getList = holder.getList
            val maxList = listOf(5, 5, 64, 64, 9, 5, 5)

            event.isCancelled = true

            fun c(t: Int, giveGold: Long? = null, giveCash: Long? = null) {
                if (getList[t]) {
                    player.sendMessage(gc("&c이미 보상을 획득 하였습니다."))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                    return
                }

                if (progressList[t] < maxList[t]) {
                    player.sendMessage(gc("&c${maxList[t] - progressList[t]}회가 부족합니다."))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                    return
                }

                if (giveGold != null && giveGold != 0L) {
                    saveData(plugin, player, "gold", getData(plugin, player, "gold").toLong() + giveGold)
                    addMakeGold(plugin, giveGold)
                }

                if (giveCash != null && giveCash != 0L) {
                    saveData(plugin, player, "cash", getData(plugin, player, "cash").toLong() + giveCash)
                    addMakeGold(plugin, giveCash * 10_000)
                }

                saveData(plugin, player, "mission/weekly/get/$t", 1)

                player.sendMessage(gc("&a보상 획득이 완료 되었습니다."))
                player.playSound(player.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 2f)
                WeeklyGui(plugin).openGui(player, 0f)
                return
            }

            when (slot) {
                19 -> c(0, 10000)
                20 -> c(1, 10000)
                21 -> c(2, 10000)
                22 -> c(3, 10000)
                23 -> c(4, 10000)
                24 -> c(5, 10000)
                25 -> c(6, 0, 8)

                48 -> DailyGui(plugin).openGui(player)
                49 -> WeeklyGui(plugin).openGui(player)
                50 -> AchievementGui(plugin).openGui(player, 0)
            }
        }
    }
}