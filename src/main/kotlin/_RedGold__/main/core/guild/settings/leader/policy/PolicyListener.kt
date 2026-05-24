package _RedGold__.main.core.guild.settings.leader.policy

import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.modify
import _RedGold__.main.functions.plainDisplayName
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

@RequireListener
class PolicyListener : Listener {
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val holder = event.inventory.holder as? PolicyHolder?: return

        taskAsync {
            savePolicyData(holder.policyData)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        val holder = gui.holder as? PolicyHolder?: return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val slot = event.slot

        val index = slot - 11
        val (comp, item) = policyDisplayItemList.getOrNull(index)?: return

        val currentStatus = comp?.get(holder.policyData)?: return
        val nexStatus = !currentStatus

        comp.set(holder.policyData, nexStatus)
        gui.item[slot] = item.modify { if (nexStatus) enchantEffect() }

        if (nexStatus) player.good("${item.plainDisplayName}&f&l(을)를 &a&l허용&f&l(으)로 변경하였습니다.")
        else player.good("${item.plainDisplayName}&f&l(을)를 &a&l거부&f&l(으)로 변경하였습니다.")
    }
}