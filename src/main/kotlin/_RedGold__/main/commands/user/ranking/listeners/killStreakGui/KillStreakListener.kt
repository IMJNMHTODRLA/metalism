package _RedGold__.main.commands.user.ranking.listeners.killStreakGui

import _RedGold__.main.commands.user.ranking.listeners.GlobalConst
import _RedGold__.main.commands.user.ranking.listeners.GlobalValue
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.FastBoolean.trueRun
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.data
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import java.time.LocalDateTime

@RequireListener
class KillStreakListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is KillStreakHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val uuid = player.uniqueId
        val slot = event.slot
        val holder = gui.holder as KillStreakHolder

        when (slot) {
            45 -> if (holder.page > 0) KillStreakGui().openGui(player, holder.page - 1)
            53 -> KillStreakGui().openGui(player, holder.page + 1)

            48 -> {
                val (rank, _, _) = GlobalValue.killStreakRank[uuid]?: run {
                    player.fail("&c순위 정보가 없습니다.")
                    return
                }

                KillStreakGui().openGui(
                    player, GlobalConst.getTeleportRanking(rank)
                )

                player.sendSound(Sound.ENTITY_ENDERMAN_TELEPORT)
            }

            50 -> {
                val hasDailyRewardSet = KillStreakValue.hasDailyRewardSet
                val now = LocalDateTime.now()
                val today4PM = now.withHour(16).withMinute(0).withSecond(0).withNano(0)

                if (now.isBefore(today4PM)) {
                    player.fail("&c&l일일 보상은 오후 4시 이후에 획득이 가능합니다.")
                    return
                }

                (uuid in hasDailyRewardSet).trueRun {
                    player.fail("&c&l이미 일일 보상을 획득 하였습니다.")
                    return
                }

                val (giveGold, giveCrystal) = KillStreakConst.dailyReward(uuid)?: run {
                    player.fail("&c순위 정보가 없습니다.")
                    return
                }

                hasDailyRewardSet.add(uuid)
                player.data.gold += giveGold
                player.data.crystal += giveCrystal

                player.good("&a&l일일 보상 획득이 완료되었습니다.")
            }
        }
    }
}