package _RedGold__.main.command.shop.sys.cashShop.cashGui

import _RedGold__.main.command.shop.sys.cashShop.deathGui.DeathGui
import _RedGold__.main.command.shop.sys.cashShop.joinGui.JoinGui
import _RedGold__.main.command.shop.sys.cashShop.killGui.KillGui
import _RedGold__.main.command.shop.sys.cashShop.kitGui.KitGui
import _RedGold__.main.command.shop.sys.cashShop.styleGui.StyleGui
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class CashListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is CashHolder) {
            val player = event.whoClicked as Player
            val slot = event.slot
            event.isCancelled = true

            when (slot) {
                4 -> KitGui(plugin).openGui(player)
                12 -> StyleGui(plugin).openGui(player)
                13 -> KillGui(plugin).openGui(player)
                14 -> DeathGui(plugin).openGui(player)
                22 -> JoinGui(plugin).openGui(player)
            }
        }
    }
}