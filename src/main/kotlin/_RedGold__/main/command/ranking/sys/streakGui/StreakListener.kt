package _RedGold__.main.command.ranking.sys.streakGui

import _RedGold__.main.command.ranking.sys.Refresh.KillStreakReward
import _RedGold__.main.command.ranking.sys.Refresh.RankValue.killStreakReward
import _RedGold__.main.function.Color.fail
import _RedGold__.main.function.Color.good
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Data.getDataUuid
import _RedGold__.main.function.Data.saveDataUuid
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class StreakListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is StreakHolder) {
            val player = event.whoClicked as Player
            val uuid = player.uniqueId
            val slot = event.slot
            val gui = event.inventory
            val holder = gui.holder as StreakHolder
            event.isCancelled = true

            when (slot) {
                45 -> if (holder.page != 0) StreakGui().openGui(player, holder.page - 1)
                53 -> StreakGui().openGui(player, holder.page + 1)

                50 -> {
                    val reward = killStreakReward[uuid]
                    val gold = reward?.gold?: 0f
                    val exp = reward?.exp?: 0f

                    if (gold <= 0f && exp <= 0f) {
                        player.fail("&c&l획득 가능한 보상이 없습니다.")
                        return
                    }

                    killStreakReward[uuid] = KillStreakReward(0f, 0f)
                    saveDataUuid(plugin, uuid, "gold",
                        getDataUuid(plugin, uuid, "gold").toLong() + gold.toLong()
                    )
                    player.giveExp(exp.toInt())

                    player.good("&a&l보상 획득이 완료되었습니다.")
                    StreakGui().openGui(player, holder.page)
                }
            }
        }
    }
}