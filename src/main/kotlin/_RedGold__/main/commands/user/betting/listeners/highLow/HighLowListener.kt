package _RedGold__.main.commands.user.betting.listeners.highLow

import _RedGold__.main.commands.user.betting.listeners.GlobalConst
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.FastReplace.fill
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.PlusMath.pow
import _RedGold__.main.functions.launch
import _RedGold__.main.functions.modify
import _RedGold__.main.functions.modifyMeta
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
class HighLowListener : Listener {
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player as? Player?: return
        val holder = event.inventory.holder as? HighLowHolder?: return
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
        if (gui.holder !is HighLowHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val slot = event.slot
        val holder = gui.holder as HighLowHolder
        val threadLocalRandom = GlobalConst.threadLocalRandom

        if (holder.isStart) return

        when (slot) {
            22 -> {
                if (holder.betGold <= 0) {
                    player.fail("&c베팅 금액을 설정하지 않았습니다.")
                    return
                }

                if (holder.select == null) {
                    player.fail("&c50초과/미만 또는 정확히 50중 에서 골라주세요.")
                    return
                }

                if (player.data.gold - holder.betGold < 0) {
                    player.fail("&c베팅 금액이 보유 골드를 초과하였습니다.")
                    return
                }

                if (holder.betGold > HighLowConst.MAX_BET) {
                    player.fail("&c베팅 금액이 너무 높습니다.")
                    return
                }

                holder.isStart = true
                player.data.gold -= holder.betGold

                val result = threadLocalRandom.nextInt(100) + 1 //0~100사이

                launch {
                    repeat(60) { i ->
                        gui.item[22] = getItem(
                            HighLowConst.GEN_RANDOM,
                            "&7&l숫자 돌리는 중${".".repeat((i % 3) + 1)}",
                        )
                        player.sendSound(Sound.BLOCK_NOTE_BLOCK_PLING)

                        delay(1.ticks)
                    }

                    val resultDisplay = HighLowConst.RANDOM_ITEM(result)

                    gui.item[22] = getItem(
                        resultDisplay.first,
                        "&f&l결과: &7&l${resultDisplay.second}",
                    ).modifyMeta { setMaxStackSize(99) }
                    .modify {
                        amount = result
                        enchantEffect()
                    }

                    player.sendSound(Sound.ENTITY_PLAYER_LEVELUP)

                    delay(20.ticks)

                    holder.isStart = false
                    player.closeInventory()

                    if ((result < 50 && holder.select == 0) || (result > 50 && holder.select == 2)) {
                        val giveGold = (holder.betGold * 1.5).toLong()

                        player.sendMsg("&aHIGHLOW 도박에 성공하였습니다. 획득 골드: ${giveGold.toFormat()} 골드")
                        player.sendSound(Sound.ENTITY_PLAYER_LEVELUP, 1.5f)

                        player.data.gold += giveGold
                        return@launch
                    }

                    if (result == 50 && holder.select == 1) {
                        val giveGold = holder.betGold * 10

                        player.sendMsg("&aHIGHLOW 도박에 성공하였습니다. 획득 골드: ${giveGold.toFormat()} 골드")
                        player.sendSound(Sound.ENTITY_PLAYER_LEVELUP, 1.5f)

                        player.data.gold += giveGold
                        return@launch
                    }

                    player.sendMsg("&cHIGHLOW 도박에 실패하였습니다. 잃은 골드: ${holder.betGold.toFormat()} 골드")
                    player.sendSound(Sound.ENTITY_ENDER_DRAGON_HURT, 1.5f)
                }
            }

            in 30..32 -> {
                val select = slot - 30

                gui.item[30] = getItem(
                    Material.REDSTONE,
                    "&c&l50 미만",
                    listOf("", "&7클릭 시 50 미만으로 선택 됩니다.")
                )

                gui.item[31] = getItem(
                    Material.CHISELED_STONE_BRICKS,
                    "&7&l정확히 50",
                    listOf("", "&7클릭 시 50으로 선택 됩니다.")
                )

                gui.item[32] = getItem(
                    Material.EMERALD,
                    "&a&l50 초과",
                    listOf("", "&7&l클릭 시 50 초과로 선택 됩니다.")
                )

                gui.item[slot] = gui.getItem(slot)?.modify { enchantEffect() }

                holder.select = select
                player.sendSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
            }

            in 45..53 -> {
                val change = when (slot) {
                    in 45..48 -> -(GlobalConst.DEFAULT_GOLD * 10.pow(48 - slot)) // 차감은 음수로
                    in 50..53 -> GlobalConst.DEFAULT_GOLD * 10.pow(slot - 50) // 추가는 양수로
                    else -> return
                }
                val newAmount = holder.betGold + change

                if (newAmount < 0) {
                    player.fail("&c베팅 금액을 음수로 내릴 수 없습니다.")
                    return
                }

                if (newAmount > HighLowConst.MAX_BET) {
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