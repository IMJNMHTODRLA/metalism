package _RedGold__.main.commands.user.betting.listeners.coinGui

import _RedGold__.main.commands.user.betting.listeners.GlobalConst
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.FastReplace.fill
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.PlusMath.pow
import _RedGold__.main.functions.launch
import _RedGold__.main.functions.task
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.data
import com.github.shynixn.mccoroutine.bukkit.ticks
import kotlinx.coroutines.delay
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

@RequireListener
class CoinListener : Listener {
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player as? Player?: return
        val holder = event.inventory.holder as? CoinHolder?: return
        if (!holder.isStart) return

        task(1) {
            if (!player.isOnline) return@task
            if (!holder.isStart) return@task

            player.openInventory(event.inventory)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is CoinHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val slot = event.slot
        val holder = gui.holder as CoinHolder
        val threadLocalRandom = GlobalConst.threadLocalRandom

        if (holder.isStart) return
        when (slot) {
            20, 24 -> {
                if (holder.betGold <= 0) {
                    player.fail("&c베팅 금액을 설정하지 않았습니다.")
                    return
                }

                if (player.data.gold - holder.betGold < 0) {
                    player.fail("&c베팅 금액이 보유 골드를 초과하였습니다.")
                    return
                }

                if (holder.betGold > CoinConst.MAX_BET) {
                    player.fail("&c베팅 금액이 너무 높습니다.")
                    return
                }

                holder.sniffling = slot == 20 //20이면 true, 24면 false
                holder.isStart = true

                player.data.gold -= holder.betGold
                val result = threadLocalRandom.nextBoolean() //true == 앞면 //false == 뒷면

                launch {
                    repeat(61) { i ->
                        gui.item[22] = getItem(
                            CoinConst.GEN_RANDOM,
                            "&7&l동전 던지는 중${".".repeat((i % 3) + 1)}"
                        )
                        player.sendSound(Sound.BLOCK_NOTE_BLOCK_PLING)

                        delay(1.ticks)
                    }

                    repeat(3) { i ->
                        gui.item[22] = getItem(
                            Material.BLACK_CONCRETE,
                            "&7&l결과는${".".repeat((i % 3) + 1)}"
                        )
                        player.sendSound(Sound.BLOCK_NOTE_BLOCK_PLING)

                        delay(3.ticks)
                    }

                    delay(30.ticks)
                    val resultDisplay = CoinConst.RANDOM_ITEM(result)

                    gui.item[22] = getItem(
                        resultDisplay.first,
                        "${resultDisplay.second}&f&l!!"
                    )

                    delay(20.ticks)

                    holder.isStart = false
                    player.closeInventory()

                    if (result == holder.sniffling) {
                        val giveGold = (holder.betGold * 1.5).toLong()

                        player.sendMsg("&a동전 던지기 도박에 성공하였습니다. 획득 골드: ${giveGold.toFormat()} 골드")
                        player.sendSound(Sound.ENTITY_PLAYER_LEVELUP, 1.5f)

                        player.data.gold += giveGold
                        return@launch
                    }

                    player.sendMsg("&c동전 던지기 도박에 실패하였습니다. 잃은 골드: ${holder.betGold.toFormat()} 골드")
                    player.sendSound(Sound.ENTITY_ENDER_DRAGON_HURT, 1.5f)
                }
            }

            in 45..53 -> {
                val change = when(slot) {
                    in 45..48 -> -(GlobalConst.DEFAULT_GOLD * 10.pow(48 - slot)) // 차감은 음수로
                    in 50..53 -> GlobalConst.DEFAULT_GOLD * 10.pow(slot - 50) // 추가는 양수로
                    else -> return
                }
                val newAmount = holder.betGold + change

                if (newAmount < 0) {
                    player.fail("&c베팅 금액을 음수로 내릴 수 없습니다.")
                    return
                }

                if (newAmount > CoinConst.MAX_BET) {
                    player.fail("&c베팅 금액을 더 이상 높힐 수 없습니다.")
                    return
                }

                holder.betGold = newAmount

                gui.item[49] = getItem(
                    Material.GRAY_STAINED_GLASS_PANE,
                    GlobalConst.BET_GOLD_MESSAGE.fill(
                        "gold" to holder.betGold.toFormat()
                    ).gc()
                )

                if (change < 0) {
                    player.sendMsg("&c베팅 금액에서 ${(-change).toFormat()} 골드를 회수하였습니다. 베팅 금액: ${holder.betGold.toFormat()}")
                    player.sendSound(Sound.ENTITY_ENDERMAN_TELEPORT, 2f)
                } else {
                    player.sendMsg("&a베팅 금액에 ${change.toFormat()} 골드를 추가하였습니다. 베팅 금액: ${holder.betGold.toFormat()}")
                    player.sendSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f)
                }
            }
        }
    }
}