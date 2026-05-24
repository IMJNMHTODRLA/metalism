package _RedGold__.main.commands.user.boost.listeners.settings.vip.vipGui

import _RedGold__.main.commands.user.boost.listeners.settings.vip.attackParticleEffectGui.AttackParticleEffectGui
import _RedGold__.main.commands.user.boost.listeners.settings.vip.chatGGColorGui.ChatGGColorGui
import _RedGold__.main.commands.user.boost.listeners.settings.vip.jumpParticleEffectGui.JumpParticleEffectGui
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class VipListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is VipHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player

        when (event.slot) {
            12 -> ChatGGColorGui().openGui(player, 0)
            13 -> AttackParticleEffectGui().openGui(player, 0)
            14 -> JumpParticleEffectGui().openGui(player, 0)
        }
    }
}