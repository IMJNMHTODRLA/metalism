package _RedGold__.main.commands.user.mission.listeners.achievementGui

import _RedGold__.main.commands.user.mission.listeners.dailyGui.DailyGui
import _RedGold__.main.commands.user.mission.listeners.weeklyGui.WeeklyGui
import _RedGold__.main.functions.Color.fail
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
class AchievementListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is AchievementHolder) {
            val player = event.whoClicked as Player
            val holder = event.inventory.holder as AchievementHolder
            val slot = event.slot

            val page = holder.page
            val progressList = holder.progressList
            val getList = holder.getList

            val maxList = listOf(
                50, 100, 150, 200, 50, 100, 6,
                12, 100, 200, 2500, 500, 7500, 10000,
                2500, 500, 7500, 10000, 100, 150, 200,
                100, 200, 100, 200, 300, 10, 20, 30,
            )

            event.isCancelled = true

            fun c(t: Int, giveCash: Long, giveStyle: Int? = null) {
                if (getList[t]) {
                    player.fail("&c이미 보상을 획득 하였습니다.")
                    return
                }

                if (progressList[t] < maxList[t]) {
                    player.fail("&c${maxList[t] - progressList[t]}회가 부족합니다.")
                    return
                }

                saveData(plugin, player, "cash", getData(plugin, player, "cash").toLong() + giveCash)
                addMakeGold(plugin, giveCash * 10_000)

                if (giveStyle != null) saveData(plugin, player, "style/$giveStyle", 1)

                saveData(plugin, player, "mission/achievement/get/$t", 1)

                player.sendMessage(gc("&a보상 획득이 완료 되었습니다."))
                player.playSound(player.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 2f)
                AchievementGui(plugin).openGui(player, 0, 0f)
                return
            }

            when (slot) {
                48 -> DailyGui(plugin).openGui(player)
                49 -> WeeklyGui(plugin).openGui(player)
                50 -> AchievementGui(plugin).openGui(player, 0)
            }

            if (page == 0) {
                when (slot) {
                    10 -> c(0, 40)
                    11 -> c(1, 40)
                    12 -> c(2, 40)
                    13 -> c(3, 80, 0)
                    14 -> c(4, 80)
                    15 -> c(5, 80, 1)
                    16 -> c(6, 80)

                    19 -> c(7, 80, 2)
                    20 -> c(8, 20)
                    21 -> c(9, 20)
                    22 -> c(10, 40)
                    23 -> c(11, 40)
                    24 -> c(12, 40)
                    25 -> c(13, 80, 3)

                    28 -> c(14, 40)
                    29 -> c(15, 40)
                    30 -> c(16, 40)
                    31 -> c(17, 80, 4)
                    32 -> c(18, 20)
                    33 -> c(19, 20)
                    34 -> c(20, 20)
                }
            } else if (page == 1) {
                when (slot) {
                    10 -> c(21, 20)
                    11 -> c(22, 20)
                    12 -> c(23, 20)
                    13 -> c(24, 20)
                    14 -> c(25, 20)
                    15 -> c(26, 40)
                    16 -> c(27, 40)

                    19 -> c(28, 40)
                }
            }
        }
    }
}