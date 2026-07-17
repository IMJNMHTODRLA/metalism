package _RedGold__.main.core.guild.homeManager.setHome

import _RedGold__.main.core.guild.homeManager.setGuildHome
import _RedGold__.main.core.guild.playerCooldownMsg
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.OVER_WORLD
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class SetHomeListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        val holder = gui.holder as? SetHomeHolder ?: return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val location = player.location

        if (event.slot != 13) return
        if (location.world.name != OVER_WORLD) {
            player.fail("&c&l오버월드가 아닙니다.")
            return
        }
        if (playerCooldownMsg(player)) return

        player.closeInventory()
        player.good("&a&l길드 홈 설정이 완료되었습니다.")

        taskAsync {
            setGuildHome(holder.id, location)
        }
    }
}