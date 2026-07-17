package _RedGold__.main.core.guild.settings.member.leave

import _RedGold__.main.core.guild.playerCooldownMsg
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.loads.RequireListener
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class LeaveListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        val player = event.whoClicked as Player
        val uuid = player.uniqueId

        val holder = gui.holder as? LeaveHolder?: return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        if (event.slot != 13) return
        if (playerCooldownMsg(player)) return

        if (holder.deleteTimes == 1) {
            player.closeInventory()
            player.good("&a&l길드 탈퇴가 완료되었습니다.")

            taskAsync {
                leaveGuild(uuid)
            }
            return
        }

        holder.deleteTimes -= 1

        gui.item[13] = getItem(
            Material.BARRIER,
            "&f&l길드 탈퇴를 원할 경우 &c&l${holder.deleteTimes}번 더 클릭해주세요.",
            "",
            "&e&l길드 탈퇴 시 다시 가입 가능합니다,"
        )
    }
}