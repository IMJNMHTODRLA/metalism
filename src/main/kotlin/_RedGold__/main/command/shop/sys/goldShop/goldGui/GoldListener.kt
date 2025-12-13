package _RedGold__.main.command.shop.sys.goldShop.goldGui

import _RedGold__.main.command.shop.sys.goldShop.cpvpGui.CpvpGui
import _RedGold__.main.command.shop.sys.goldShop.enchantGui.EnchantGui
import _RedGold__.main.command.shop.sys.goldShop.foodGui.FoodGui
import _RedGold__.main.command.shop.sys.goldShop.mineralGui.MineralGui
import _RedGold__.main.command.shop.sys.goldShop.plantGui.PlantGui
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.plugin.java.JavaPlugin

@RequireListener
@RequireJavaPlugin
class GoldListener(private val plugin: JavaPlugin) : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        if (event.inventory.holder is GoldHolder) {
            val player = event.whoClicked as Player
            val slot = event.slot
            event.isCancelled = true

            when (slot) {
                4 -> EnchantGui().openGui(player, 1)
                12 -> FoodGui().openGui(player, 1)
                13 -> CpvpGui().openGui(player)
                14 -> MineralGui().openGui(player)
                22 -> PlantGui(plugin).openGui(player)
            }
        }
    }
}