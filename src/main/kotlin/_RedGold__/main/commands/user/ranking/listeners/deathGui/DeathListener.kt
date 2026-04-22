package _RedGold__.main.commands.user.ranking.listeners.deathGui

import _RedGold__.main.commands.user.ranking.listeners.GlobalConst
import _RedGold__.main.commands.user.ranking.listeners.GlobalValue
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.loads.RequireListener
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class DeathListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is DeathHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val uuid = player.uniqueId
        val holder = gui.holder as DeathHolder
        val slot = event.slot

        when (slot) {
            45 -> if (holder.page > 0) DeathGui().openGui(player, holder.page - 1)
            53 -> DeathGui().openGui(player, holder.page + 1)

            49 -> {
                val (rank, _, _) = GlobalValue.deathRank[uuid]?: run {
                    player.fail("&c순위 정보가 없습니다.")
                    return
                }

                DeathGui().openGui(
                    player, GlobalConst.getTeleportRanking(rank)
                )

                player.sendSound(Sound.ENTITY_ENDERMAN_TELEPORT)
            }
        }
    }
}