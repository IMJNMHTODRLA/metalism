package _RedGold__.main.commands.user.cosmetic.listeners.selectGui

import _RedGold__.main.commands.user.cosmetic.listeners.cosmeticGui.CosmeticGui
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.FastBoolean.falseRun
import _RedGold__.main.functions.FastBoolean.trueRun
import _RedGold__.main.functions.FastNumber.seconds
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.variableManager.cosmeticManager.CosmeticEnum
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
class SelectListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is SelectHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val slot = event.slot

        when(slot) {
            11 -> CosmeticGui().openGui(player, CosmeticEnum.STYLE)
            12 -> CosmeticGui().openGui(player, CosmeticEnum.JOIN)
            13 -> CosmeticGui().openGui(player, CosmeticEnum.DEATH)
            14 -> CosmeticGui().openGui(player, CosmeticEnum.KILL)
        }
    }
}