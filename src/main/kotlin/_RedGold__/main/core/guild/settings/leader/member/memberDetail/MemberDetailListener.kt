package _RedGold__.main.core.guild.settings.leader.member.memberDetail

import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class MemberDetailListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        val holder = gui.holder as? MemberDetailHolder?: return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val slot = event.slot

        val id = holder.data.id
        val newLeader = holder.data.players.firstOrNull()?: return

        when(slot) {
            12 -> startUtilityLogic(player, holder.target, "&6&l리더 권한 이전") {
                transferLeader(id, newLeader)
            }

            13 -> startUtilityLogic(player, holder.target, "&4&l차단") {
                blockPlayer(id, newLeader)
            }

            14 -> startUtilityLogic(player, holder.target, "&c&l추방") {
                kickPlayer(id, newLeader)
            }
        }
    }
}