package _RedGold__.main.commands._event_.sys.eventGui

import _RedGold__.main.Main.Event.EVENT_CODE
import _RedGold__.main.Main.Event.EVENT_NAME
import _RedGold__.main.commands._event_.sys.rankRewardGui.RankRewardGui
import _RedGold__.main.commands._event_.sys.rewardGui.RewardGui
import _RedGold__.main.event._showDown_.System.RandomEffectEvent.bestPoint
import _RedGold__.main.event._showDown_.System.RandomEffectEvent.difficulty
import _RedGold__.main.event._showDown_.System.RandomEffectEvent.max
import _RedGold__.main.event._showDown_.System.RandomEffectEvent.point
import _RedGold__.main.functions.Color.gc
import _RedGold__.main.function.Data.hasData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class EventListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is EventHolder) {
            val player = event.whoClicked as Player
            val uuid = player.uniqueId
            val slot = event.slot
            val clickType = event.click
            event.isCancelled = true

            if (clickType == ClickType.LEFT) {
                when (slot) {
                    21 -> {
                        if (hasData(plugin, player, "$EVENT_CODE/join")) {
                            player.sendMessage(gc("&c당신은 이미 $EVENT_NAME &c이벤트에 참가하였습니다."))
                            player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f)
                            return
                        }

                        saveData(plugin, player, "$EVENT_CODE/join", "")
                        saveData(plugin, player, "$EVENT_CODE/point", 0)
                        saveData(plugin, player, "$EVENT_CODE/best_point", 0)
                        saveData(plugin, player, "$EVENT_CODE/max", 0)
                        saveData(plugin, player, "$EVENT_CODE/rank_reward", 0)
                        for (i in 0..55) saveData(plugin, player, "$EVENT_CODE/get/$i", 0)
                        difficulty[uuid] = 0
                        point[uuid] = 0L
                        bestPoint[uuid] = 0L
                        max[uuid] = 0

                        player.sendMessage(gc("$EVENT_NAME &a이벤트 참가가 완료되었습니다."))
                        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                        return
                    }
                    22 -> RankRewardGui().openGui(player, 0)
                    23 -> RewardGui(plugin).openGui(player, 1)
                }
                return
            }
        }
    }
}