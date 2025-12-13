package _RedGold__.main.command.mission.sys.dailyGui

import _RedGold__.main.command.mission.sys.achievementGui.AchievementGui
import _RedGold__.main.command.mission.sys.weeklyGui.WeeklyGui
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.Gui.getItem
import _RedGold__.main.function.ServerGold.addHoldGold
import _RedGold__.main.function.ServerGold.addMakeGold
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
class DailyListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is DailyHolder) {
            val player = event.whoClicked as Player
            val holder = event.inventory.holder as DailyHolder
            val slot = event.slot

            val progressList = holder.progressList
            val getList = holder.getList
            val maxList = listOf(1, 1, 100, 100, 3, 1, 5)

            event.isCancelled = true

            fun c(t: Int, giveGold: Long? = null, giveItem: List<ItemStack>? = null, giveCash: Long? = null) {
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

                if (giveItem != null) {
                    var emptySlots = 0
                    for (item in player.inventory.storageContents) {
                        if (item == null) emptySlots++
                    }

                    if (giveItem.size > emptySlots) {
                        player.sendMessage(gc("&c인벤토리에 ${giveItem.size}칸 이상의 빈칸이 필요합니다."))
                        player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 0.5f)
                        return
                    }

                    for (gave in giveItem) player.inventory.addItem(gave)
                }

                if (giveGold != null && giveGold != 0L) {
                    saveData(plugin, player, "gold", getData(plugin, player, "gold").toLong() + giveGold)
                    addMakeGold(plugin, giveGold)
                }

                if (giveCash != null && giveCash != 0L) {
                    saveData(plugin, player, "cash", getData(plugin, player, "cash").toLong() + giveCash)
                    addMakeGold(plugin, giveCash * 10_000)
                }

                saveData(plugin, player, "mission/daily/get/$t", 1)

                player.sendMessage(gc("&a보상 획득이 완료 되었습니다."))

                player.playSound(player.location, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 2f)
                DailyGui(plugin).openGui(player, 0f)
                return
            }

            when (slot) {
                19 -> c(0, 0, listOf(getItem("experience_bottle").apply {amount = 2}), 1)
                20 -> c(1, 0, listOf(getItem("end_crystal").apply {amount = 2}))
                21 -> c(2, 0, listOf(getItem("respawn_anchor").apply {amount = 2}))
                22 -> c(3, 0, listOf(getItem("experience_bottle").apply {amount = 4}))
                23 -> c(4, 5_000)
                24 -> c(5, 0, null, 3)
                25 -> c(6, 30_000, listOf(getItem("experience_bottle").apply {amount = 4}))

                48 -> DailyGui(plugin).openGui(player)
                49 -> WeeklyGui(plugin).openGui(player)
                50 -> AchievementGui(plugin).openGui(player)
            }
        }
    }
}