package _RedGold__.main.event._showDown_.selectGui

import _RedGold__.main.Main.Event.END_TIME
import _RedGold__.main.Main.Event.START_TIME
import _RedGold__.main.event._showDown_.DataManager
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.loads.RequireListener
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequireListener
class SelectListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is SelectHolder) {
            val player = event.whoClicked as Player
            val uuid = player.uniqueId
            val slot = event.slot
            val clickType = event.click
            event.isCancelled = true

            val difficultyMessage = listOf("&e&l보통(Normal)", "&c&l어려움(Hard)", "&4&l하드코어(HardCore)", "&b&l익스트림(Extreme)", "&d&l얼티밋(Ultimate)")

            fun difficultySelect(type: Int) {
                val now = LocalDateTime.now()
                if (now.isBefore(START_TIME) || !now.isBefore(END_TIME)) {
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    val startTimeFormat = START_TIME.format(formatter)
                    val endTimeFormat = END_TIME.format(formatter)

                    player.fail("&c대결전이 종료 되었습니다: $startTimeFormat - $endTimeFormat")
                    player.closeInventory()
                    return
                }

                DataManager.cashingPoint[uuid] = 0

                DataManager.ticket[uuid] = ticket[uuid]!! + 1
                val bonusPoint = 60_000L - (500L * elapsedTime)
                point[uuid] = point[uuid]!! + bonusPoint
                cashingPoint[uuid] = bonusPoint

                player.sendMessage(gc("${difficultyMessage[type]} &f&l난이도를 &c&l선택하였습니다.&8&l(선택 횟수: ${ticket[uuid]}/3)"))
                player.sendMessage(gc("&a&l${elapsedTime}&f&l초 안에 클릭하여 &d&l${bonusPoint.toFormat()} 점수&f&l를 획득하였습니다."))

                for (i in 0..type) player.addPotionEffect(difficultyEffect[i])

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
                }
                return
            }

            fun difficultySweep(difficulty: Int) {
                val cashingPoint = DataManager.cashingPoint[uuid]?: 0
                val ticket = DataManager.ticket[uuid]?: 0
                val point = DataManager.point[uuid]?: 0
                val cashingDifficulty = DataManager.cashingDifficulty[uuid]

                if (cashingPoint <= 0 || difficulty != cashingDifficulty) {
                    player.fail("&c소탕이 불가능 합니다.")
                    return
                }

                if (ticket <= 0) {
                    player.fail("&c&l대결전 티켓이 부족합니다.")
                    return
                }

                DataManager.ticket[uuid] = (ticket - 1).coerceAtLeast(0)
                DataManager.point[uuid] = point + cashingPoint

                player.sendMsg("&a&l소탕이 완료되었습니다!")
                player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f)

                SelectGui().openGui(player, 0f)
            }

            if (clickType == ClickType.RIGHT) {
                when (slot) {
                    11 -> difficultySweep(0)
                    12 -> difficultySweep(1)
                    13 -> difficultySweep(2)
                    14 -> difficultySweep(3)
                    15 -> difficultySweep(4)
                }
                return
            }
        }
    }
}