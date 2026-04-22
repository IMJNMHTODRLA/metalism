package _RedGold__.main.commands.user.shop.listeners.goldShop.goldGui

import _RedGold__.main.commands.user.shop.listeners.goldShop.cpvpGui.CpvpGui
import _RedGold__.main.commands.user.shop.listeners.goldShop.enchantGui.EnchantGui
import _RedGold__.main.commands.user.shop.listeners.goldShop.foodGui.FoodGui
import _RedGold__.main.commands.user.shop.listeners.goldShop.mineralGui.MineralGui
import _RedGold__.main.commands.user.shop.listeners.goldShop.plantGui.PlantGui
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class GoldListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is GoldHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val slot = event.slot

        when (slot) {
            4 -> EnchantGui().openGui(player, 1)
            12 -> FoodGui().openGui(player)
            13 -> CpvpGui().openGui(player)
            14 -> MineralGui().openGui(player)
            22 -> PlantGui().openGui(player)
        }
    }
}