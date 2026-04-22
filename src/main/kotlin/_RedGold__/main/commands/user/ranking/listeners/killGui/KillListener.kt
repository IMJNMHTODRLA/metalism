package _RedGold__.main.commands.user.ranking.listeners.killGui

import _RedGold__.main.commands.user.ranking.listeners.GlobalConst
import _RedGold__.main.commands.user.ranking.listeners.GlobalValue
import _RedGold__.main.commands.user.ranking.listeners.boostGui.BoostGui
import _RedGold__.main.commands.user.ranking.listeners.boostGui.BoostHolder
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.loads.RequireListener
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class KillListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is KillHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val uuid = player.uniqueId
        val holder = gui.holder as KillHolder
        val slot = event.slot

        when (slot) {
            45 -> if (holder.page > 0) KillGui().openGui(player, holder.page - 1)
            53 -> KillGui().openGui(player, holder.page + 1)

            49 -> {
                val (rank, _, _) = GlobalValue.killRank[uuid]?: run {
                    player.fail("&c순위 정보가 없습니다.")
                    return
                }

                KillGui().openGui(
                    player, GlobalConst.getTeleportRanking(rank)
                )

                player.sendSound(Sound.ENTITY_ENDERMAN_TELEPORT)
            }
        }
    }
}