package _RedGold__.main.event.randomEffect.selectGui

import _RedGold__.main.Main.Event.END_TIME
import _RedGold__.main.Main.Event.EVENT_CODE
import _RedGold__.main.Main.Event.EVENT_NAME
import _RedGold__.main.Main.Event.START_TIME
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.difficulty
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.difficultyEffect
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.killEvent1
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.killEvent2
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.max
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.point
import _RedGold__.main.function.Color.fail
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Data.hasData
import _RedGold__.main.function.Data.saveData
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.plugin.java.JavaPlugin
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequireListener
class SelectListener : Listener {
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        if (event.inventory.holder is SelectHolder) {
            val player = event.player as Player
            val uuid = player.uniqueId
            val holder = event.inventory.holder as SelectHolder

            if (!holder.isClose) {
                difficulty[uuid] = 0

                killEvent1.remove(uuid)
                killEvent2.remove(uuid)

                player.sendMessage(gc("&7&lESC 키를 눌러 난이도 선택을 취소하였습니다.&8&l(선택 횟수: ${max[uuid]}/3)"))
                player.playSound(player.location, Sound.ENTITY_PLAYER_ATTACK_NODAMAGE, 1f, 1f)
            }
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is SelectHolder) {
            val player = event.whoClicked as Player
            val uuid = player.uniqueId
            val slot = event.slot
            val clickType = event.click
            val holder = event.inventory.holder as SelectHolder
            val openTime = holder.openTime
            event.isCancelled = true

            val difficultyMessage = listOf("&e&l보통(Normal)", "&c&l어려움(Hard)", "&4&l하드코어(HardCore)", "&b&l익스트림(Extreme)", "&d&l얼티밋(Ultimate)")

            fun difficultySelect(type: Int) {
                holder.isClose = true

                val now = LocalDateTime.now()
                if (now.isBefore(START_TIME) || !now.isBefore(END_TIME)) {
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    val startTimeFormat = START_TIME.format(formatter)
                    val endTimeFormat = END_TIME.format(formatter)

                    player.fail("&c이벤트가 종료 되었습니다: $startTimeFormat - $endTimeFormat")
                    player.closeInventory()
                    return
                }

                val elapsedTime = (System.currentTimeMillis() / 1000) - openTime
                max[uuid] = max[uuid]!! + 1

                if (elapsedTime > 120L) {
                    player.sendMessage(gc("&c&l2분 안에 선택을 안하여 난이도 선택이 취소되었습니다.&8&l(선택 횟수: ${max[uuid]}/4)"))
                    player.playSound(player.location, Sound.ENTITY_PLAYER_ATTACK_NODAMAGE, 1f, 1f)
                    return
                }

                val bonusPoint = 60_000L - (500L * elapsedTime)
                point[uuid] = point[uuid]!! + bonusPoint
                killEvent1.remove(uuid)
                killEvent2.remove(uuid)
                difficulty[uuid] = type + 1

                player.sendMessage(gc("${difficultyMessage[type]} &f&l난이도를 &c&l선택하였습니다.&8&l(선택 횟수: ${max[uuid]}/3)"))
                player.sendMessage(gc("&a&l${elapsedTime}&f&l초 안에 클릭하여 &d&l${bonusPoint.toFormat()} 점수&f&l를 획득하였습니다."))

                for (i in 0..<(type + 1)) player.addPotionEffect(difficultyEffect[i])

                player.playSound(player.location, Sound.ENTITY_ENDER_DRAGON_AMBIENT, 1f, 1f)
                player.closeInventory()
            }

            if (clickType == ClickType.LEFT) {
                when (slot) {
                    11 -> difficultySelect(0)
                    12 -> difficultySelect(1)
                    13 -> difficultySelect(2)
                    14 -> difficultySelect(3)
                    15 -> difficultySelect(4)
                    22 -> {
                        holder.isClose = true

                        killEvent1.remove(uuid)
                        killEvent2.remove(uuid)
                        //max[uuid] = max[uuid]!! + 1
                        difficulty[uuid] = 0

                        player.sendMessage(gc("&7&l난이도를 선택 안하였습니다.&8&l(선택 횟수: ${max[uuid]}/3)"))

                        player.playSound(player.location, Sound.ENTITY_PLAYER_ATTACK_NODAMAGE, 1f, 1f)
                        player.closeInventory()
                    }
                }
                return
            }
        }
    }
}