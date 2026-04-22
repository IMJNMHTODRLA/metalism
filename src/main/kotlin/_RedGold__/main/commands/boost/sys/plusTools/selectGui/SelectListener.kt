package _RedGold__.main.commands.boost.sys.plusTools.selectGui

import _RedGold__.main.commands.boost.sys.plusTools.ggColorGui.GgColorGui
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class SelectListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is SelectHolder) {
            val player = event.whoClicked as Player
            val slot = event.slot
            event.isCancelled = true

            when (slot) {
                3 -> GgColorGui(plugin).openGui(player, 1)
            }
        }
    }
}