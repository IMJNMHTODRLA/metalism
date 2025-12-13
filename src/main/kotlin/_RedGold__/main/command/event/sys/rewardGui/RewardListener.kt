package _RedGold__.main.command.event.sys.rewardGui

import _RedGold__.main.command.mission.sys.dailyGui.DailyGui
import _RedGold__.main.command.mission.sys.weeklyGui.WeeklyGui
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.point
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.ServerGold.addMakeGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin


@RequireJavaPlugin
@RequireListener
class RewardListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is RewardHolder) {
            val player = event.whoClicked as Player
            val uuid = player.uniqueId
            val holder = event.inventory.holder as RewardHolder
            val slot = event.slot
            val point = point[uuid]?: 0L

            val getList = holder.getList
            val page = holder.page

            event.isCancelled = true

            fun c(t: Int, giveToken: Long, giveAdvancedToken: Long? = null) {
                if (getList[t]) {
                    player.sendMessage(gc("&c이미 보상을 획득 하였습니다."))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                    return
                }

                val max = (t + 1) * 10_000_000

                if (point < max) {
                    player.sendMessage(gc("&c${(max - point).toFormat()}점수가 부족합니다."))
                    player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                    return
                }

                saveData(plugin, player, "gold", getData(plugin, player, "gold").toLong() + 100000)
                saveData(plugin, player, "cash", getData(plugin, player, "cash").toLong() + 10)
                addMakeGold(plugin, 200000)

                saveData(plugin, player, "token/normal", getData(plugin, player, "token/normal").toLong() + giveToken)
                addMakeGold(plugin, giveToken * 10000L)

                if (giveAdvancedToken != null) {
                    saveData(plugin, player, "token/advanced", getData(plugin, player, "token/advanced").toLong() + giveAdvancedToken)
                    addMakeGold(plugin, giveAdvancedToken * 50_000)
                }

                saveData(plugin, player, "randomEffect/get/$t", 1)

                player.sendMessage(gc("&a보상 획득이 완료 되었습니다."))

                player.playSound(player.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 2f)
                RewardGui(plugin).openGui(player, page, 0f)
                return
            }

            if (page == 1) {
                when (slot) {
                    10 -> c(0, 10)
                    11 -> c(1, 10)
                    12 -> c(2, 10)
                    13 -> c(3, 10)
                    14 -> c(4, 10)
                    15 -> c(5, 10)
                    16 -> c(6, 20)

                    19 -> c(7, 30)
                    20 -> c(8, 40)
                    21 -> c(9, 50)
                    22 -> c(10, 60)
                    23 -> c(11, 70)
                    24 -> c(12, 80)
                    25 -> c(13, 90)

                    28 -> c(14, 100)
                    29 -> c(15, 110)
                    30 -> c(16, 120)
                    31 -> c(17, 130)
                    32 -> c(18, 140, 10)
                    33 -> c(19, 150, 20)
                    34 -> c(20, 160, 30)

                    37 -> c(21, 160, 40)
                    38 -> c(22, 170, 50)
                    39 -> c(23, 180, 60)
                    40 -> c(24, 190, 70)
                    41 -> c(25, 200, 80)
                    42 -> c(26, 20, 90)
                    43 -> c(27, 20, 100)

                    53 -> RewardGui(plugin).openGui(player, 2)
                }
                return
            }

            if (page == 2) {
                when (slot) {
                    10 -> c(28, 20, 10)
                    11 -> c(29, 20, 10)
                    12 -> c(30, 20, 10)
                    13 -> c(31, 20, 10)
                    14 -> c(32, 20, 10)
                    15 -> c(33, 20, 10)
                    16 -> c(34, 20, 10)

                    19 -> c(35, 20, 10)
                    20 -> c(36, 20, 10)
                    21 -> c(37, 20, 10)
                    22 -> c(38, 20, 10)

                    45 -> RewardGui(plugin).openGui(player, 1)
                }
                return
            }
        }
    }
}