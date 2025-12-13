package _RedGold__.main.command.event.sys.eventGui

import _RedGold__.main.Main.Event.EVENT_CODE
import _RedGold__.main.Main.Event.EVENT_NAME
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.difficulty
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.max
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.point
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.hasData
import _RedGold__.main.function.Data.saveData
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
                        saveData(plugin, player, "$EVENT_CODE/max", 0)
                        difficulty[uuid] = 0
                        point[uuid] = 0L
                        max[uuid] = 0

                        player.sendMessage(gc("$EVENT_NAME &a이벤트 참가가 완료되었습니다."))
                        player.playSound(player.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                        return
                    }
                }
                return
            }
        }
    }
}