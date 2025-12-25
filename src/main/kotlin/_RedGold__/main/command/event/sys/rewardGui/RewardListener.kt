package _RedGold__.main.command.event.sys.rewardGui

import _RedGold__.main.command.mission.sys.dailyGui.DailyGui
import _RedGold__.main.command.mission.sys.weeklyGui.WeeklyGui
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.point
import _RedGold__.main.function.Color.fail
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.ServerGold.addMakeGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import com.google.gson.JsonObject
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

            /**
            * @param props <-
            *   골드 지급할 때는 gold 키/Long
            *   캐시는 cash 키/Long
            *   아이템은 item 키(listOf에 아이템 넣기)
            *   레벨은 level 키/Long
            * */
            fun reward(t: Int, needPoint: Long, props: Map<String, Any>) {
                if (getList[t]) {
                    player.fail("&c이미 보상을 획득 하였습니다.")
                    return
                }

                if (point < needPoint) {
                    player.fail("&c${(needPoint - point).toFormat()}점수가 부족합니다.")
                    return
                }

                val gold = props["gold"] as? Long
                val cash = props["cash"] as? Long
                val item = props["item"] as? ItemStack
                val level = props["level"] as? Int

                var makeAll = 0L
                if (gold != null) {
                    saveData(plugin, player, "gold", getData(plugin, player, "gold").toLong() + gold)
                    makeAll += gold
                }

                if (cash != null) {
                    saveData(plugin, player, "cash", getData(plugin, player, "cash").toLong() + cash)
                    makeAll += cash * 10_000
                }

                if (item != null) player.inventory.addItem(item)

                if (level != null) player.giveExpLevels(level)

                if (makeAll != 0L) addMakeGold(plugin, 200000)

                saveData(plugin, player, "randomEffect/get/$t", 1)

                player.sendMessage(gc("&a보상 획득이 완료 되었습니다."))

                player.playSound(player.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 2f)
                RewardGui(plugin).openGui(player, page, 0f)
                return
            }

            if (page == 1) {
                when (slot) {
                    10 -> reward(0, 100_000, mapOf("level" to 1))
                    11 -> reward(1, 200_000, mapOf("gold" to 300_000))
                    12 -> reward(2, 300_000, mapOf("item" to getItem("end_crystal", t = 16)))
                    13 -> reward(3, 400_000, mapOf("item" to getItem("respawn_anchor", t = 16)))
                    14 -> reward(4, 500_000, mapOf("item" to getItem("ender_pearl", t = 16)))
                    15 -> reward(5, 600_000, mapOf("item" to getItem("experience_bottle", t = 16)))
                    16 -> reward(6, 700_000, mapOf("cash" to 30))

                    19 -> reward(0, 800_000, mapOf("level" to 1))
                    20 -> reward(1, 900_000, mapOf("gold" to 500_000))
                    21 -> reward(2, 1_000_000, mapOf("item" to getItem("end_crystal", t = 24)))
                    22 -> reward(3, 1_500_000, mapOf("item" to getItem("respawn_anchor", t = 24)))
                    23 -> reward(4, 2_000_000, mapOf("item" to getItem("golden_carrot", t = 24)))
                    24 -> reward(5, 2_500_000, mapOf("item" to getItem("experience_bottle", t = 24)))
                    25 -> reward(6, 3_000_000, mapOf("cash" to 40))

                    28 -> reward(0, 3_500_000, mapOf("level" to 2))
                    29 -> reward(1, 4_000_000, mapOf("gold" to 1_000_000))
                    30 -> reward(2, 4_500_000, mapOf("item" to getItem("end_crystal", t = 48)))
                    31 -> reward(3, 5_000_000, mapOf("item" to getItem("totem_of_undying", t = 8)))
                    32 -> reward(4, 6_000_000, mapOf("item" to getItem("golden_carrot", t = 48)))
                    33 -> reward(5, 7_000_000, mapOf("item" to getItem("experience_bottle", t = 48)))
                    34 -> reward(6, 8_000_000, mapOf("cash" to 50))

                    37 -> reward(0, 9_000_000, mapOf("level" to 3))
                    38 -> reward(1, 10_000_000, mapOf("gold" to 1_500_000))
                    39 -> reward(2, 12_000_000, mapOf("item" to getItem("end_crystal", t = 64)))
                    40 -> reward(3, 14_000_000, mapOf("item" to getItem("totem_of_undying", t = 12)))
                    41 -> reward(4, 16_000_000, mapOf("item" to getItem("golden_carrot", t = 64)))
                    42 -> reward(5, 18_000_000, mapOf("item" to getItem("experience_bottle", t = 64)))
                    43 -> reward(6, 20_000_000, mapOf("cash" to 60))
                }
                return
            }
        }
    }
}