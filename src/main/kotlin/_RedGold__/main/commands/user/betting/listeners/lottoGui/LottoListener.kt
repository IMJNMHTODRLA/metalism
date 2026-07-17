package _RedGold__.main.commands.user.betting.listeners.lottoGui

import _RedGold__.main.commands.user.betting.listeners.GlobalConst
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.launch
import _RedGold__.main.functions.modify
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
class LottoListener : Listener {
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player as? Player?: return
        val holder = event.inventory.holder as? LottoHolder?: return
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
        if (gui.holder !is LottoHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val slot = event.slot
        val holder = gui.holder as LottoHolder
        val threadLocalRandom = GlobalConst.threadLocalRandom

        if (holder.isStart) return

        when (slot) {
            22 -> {
                if (player.data.gold - LottoConst.AMOUNT < 0) {
                    player.fail("&c골드가 부족합니다. 필요 골드: ${(LottoConst.AMOUNT - player.data.gold).toFormat()}")
                    return
                }

                if (holder.select.toSet().size != 6) {
                    player.fail("&c중복된 번호가 있습니다.")
                    return
                }

                player.data.gold -= LottoConst.AMOUNT
                holder.isStart = true

                if (event.isShiftClick) {
                    val result = (1..45)
                        .shuffled(threadLocalRandom)
                        .take(6)

                    repeat(6) { i ->
                        holder.select[i] = result[i]

                        gui.item[
                            if (i in 0..2) 19 + i else 20 + i
                        ] = getItem(
                            Material.GOLD_INGOT,
                            "&f&l[ &e&l${holder.select[i]} &f&l]",
                            listOf("", "&7클릭 시 ${i + 1}번째 로또 번호가 1 증가됩니다.", "&7현재 고른 로또 번호: ${holder.select}"),
                            holder.select[i]
                        )
                    }
                }

                val result = (1..45)
                    .shuffled(threadLocalRandom)
                    .take(6)

                launch {
                    repeat(6) { i ->
                        val changeSlot = if (i in 0..2) 19 + i else 20 + i

                        repeat(40) {
                            val random = threadLocalRandom.nextInt(45) + 1

                            gui.item[changeSlot] = getItem(
                                Material.GOLD_NUGGET,
                                "&8&l[ &7&l${random} &8&l]",
                                listOf("", "&7&l추첨 중...: $random"),
                                random
                            )
                            player.sendSound(Sound.BLOCK_NOTE_BLOCK_PLING)

                            delay(1.ticks)
                        }

                        val isMatch = result[i] == holder.select[i]
                        val material = if (isMatch) Material.GOLD_INGOT else Material.BLACK_CONCRETE
                        val nameColor = if (isMatch) "&6&l" else "&7&l"
                        val descLine = if (isMatch) "!" else "..."

                        gui.item[changeSlot] = getItem(
                            material,
                            "&f&l[ $nameColor${result[i]} &f&l]",
                            listOf("", "&7&l${i + 1}번째 숫자 추첨 완료$descLine"),
                        ).modify {
                            amount = result[i]

                            if (isMatch) {
                                enchantEffect()
                                player.sendSound(Sound.ENTITY_PLAYER_LEVELUP)
                            } else {
                                player.sendSound(Sound.ENTITY_LIGHTNING_BOLT_IMPACT)
                            }
                        }

                        delay(20.ticks)
                    }

                    delay(10.ticks)

                    holder.isStart = false
                    player.closeInventory()

                    val correctNum = (0..5).count { i -> result[i] == holder.select[i] }

                    player.sendMsg(LottoConst.WIN_MESSAGE[correctNum])
                    player.sendMsg("&6&l획득 골드: ${LottoConst.WIN_PRIZE[correctNum].toFormat()} 골드")

                    player.sendSound(LottoConst.WIN_SOUND[correctNum])

                    player.data.gold += LottoConst.WIN_PRIZE[correctNum]
                }
            }

            in 19..25 -> {
                val select = if (slot < 22) slot - 19 else slot - 20

                holder.select[select] = holder.select[select] % 45 + 1
                player.sendSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP)

                repeat(6) { i ->
                    gui.item[
                        if (i in 0..2) 19 + i else 20 + i
                    ] = getItem(
                        Material.GOLD_INGOT,
                        "&f&l[ &e&l${holder.select[i]} &f&l]",
                        listOf("", "&7클릭 시 ${i + 1}번째 로또 번호가 1 증가됩니다.", "&7현재 고른 로또 번호: ${holder.select}"),
                        holder.select[i]
                    )
                }
            }
        }
    }
}