package _RedGold__.main.commands._event_.sys.rewardGui

import _RedGold__.main.event._showDown_.System.RandomEffectEvent.point
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.function.ServerGold.addMakeGold
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import org.bukkit.Sound
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
                    16 -> reward(6, 700_000, mapOf("cash" to 5))

                    19 -> reward(7, 800_000, mapOf("level" to 1))
                    20 -> reward(8, 900_000, mapOf("gold" to 500_000))
                    21 -> reward(9, 1_000_000, mapOf("item" to getItem("end_crystal", t = 24)))
                    22 -> reward(10, 1_500_000, mapOf("item" to getItem("respawn_anchor", t = 24)))
                    23 -> reward(11, 2_000_000, mapOf("item" to getItem("golden_carrot", t = 24)))
                    24 -> reward(12, 2_500_000, mapOf("item" to getItem("experience_bottle", t = 24)))
                    25 -> reward(13, 3_000_000, mapOf("cash" to 10))

                    28 -> reward(14, 3_500_000, mapOf("level" to 2))
                    29 -> reward(15, 4_000_000, mapOf("gold" to 1_000_000))
                    30 -> reward(16, 4_500_000, mapOf("item" to getItem("end_crystal", t = 48)))
                    31 -> reward(17, 5_000_000, mapOf("item" to getItem("totem_of_undying", t = 8)))
                    32 -> reward(18, 6_000_000, mapOf("item" to getItem("golden_carrot", t = 48)))
                    33 -> reward(19, 7_000_000, mapOf("item" to getItem("experience_bottle", t = 48)))
                    34 -> reward(20, 8_000_000, mapOf("cash" to 50))

                    37 -> reward(21, 9_000_000, mapOf("level" to 3))
                    38 -> reward(22, 10_000_000, mapOf("gold" to 2_500_000))
                    39 -> reward(23, 12_000_000, mapOf("item" to getItem("end_crystal", t = 64)))
                    40 -> reward(24, 14_000_000, mapOf("item" to getItem("totem_of_undying", t = 12)))
                    41 -> reward(25, 16_000_000, mapOf("item" to getItem("golden_carrot", t = 64)))
                    42 -> reward(26, 18_000_000, mapOf("item" to getItem("experience_bottle", t = 64)))
                    43 -> reward(27, 20_000_000, mapOf("cash" to 50))
                }
            } else if (page == 2) {
                when (slot) {
                    10 -> reward(28, 22_000_000, mapOf("item" to getItem("totem_of_undying", t = 16)))
                    11 -> reward(29, 24_000_000, mapOf("item" to getItem("end_crystal", t = 16)))
                    12 -> reward(30, 26_000_000, mapOf("item" to getItem("golden_carrot", t = 16)))
                    13 -> reward(31, 28_000_000, mapOf("item" to getItem("experience_bottle", t = 16)))
                    14 -> reward(32, 30_000_000, mapOf("item" to getItem("respawn_anchor", t = 16)))
                    15 -> reward(33, 32_000_000, mapOf("item" to getItem("ender_pearl", t = 16)))
                    16 -> reward(34, 34_000_000, mapOf("cash" to 40))

                    19 -> reward(35, 36_000_000, mapOf("gold" to 1_000_000))
                    20 -> reward(36, 38_000_000, mapOf("gold" to 1_000_000))
                    21 -> reward(37, 40_000_000, mapOf("gold" to 1_000_000))
                    22 -> reward(38, 45_000_000, mapOf("gold" to 1_000_000))
                    23 -> reward(39, 50_000_000, mapOf("gold" to 1_000_000))
                    24 -> reward(40, 55_000_000, mapOf("gold" to 1_000_000))
                    25 -> reward(41, 60_000_000, mapOf("cash" to 40))

                    28 -> reward(42, 65_000_000, mapOf("gold" to 1_500_000))
                    29 -> reward(43, 70_000_000, mapOf("gold" to 1_500_000))
                    30 -> reward(44, 75_000_000, mapOf("gold" to 1_500_000))
                    31 -> reward(45, 80_000_000, mapOf("gold" to 1_500_000))
                    32 -> reward(46, 85_000_000, mapOf("gold" to 1_500_000))
                    33 -> reward(47, 90_000_000, mapOf("gold" to 1_500_000))
                    34 -> reward(48, 95_000_000, mapOf("cash" to 40))

                    37 -> reward(49, 100_000_000, mapOf("level" to 20))
                    38 -> reward(50, 110_000_000, mapOf("item" to getItem("obsidian", t = 64)))
                    39 -> reward(51, 120_000_000, mapOf("item" to getItem("obsidian", t = 64)))
                    40 -> reward(52, 130_000_000, mapOf("item" to getItem("glowstone", t = 64)))
                    41 -> reward(53, 140_000_000, mapOf("item" to getItem("glowstone", t = 64)))
                    42 -> reward(54, 150_000_000, mapOf("item" to getItem("golden_carrot", t = 64)))
                    43 -> reward(55, 160_000_000, mapOf("cash" to 100))
                }
            }
        }
    }
}