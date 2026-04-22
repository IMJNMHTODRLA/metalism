package _RedGold__.main.commands.user.shop.listeners.goldShop.foodGui

import _RedGold__.main.commands.user.shop.listeners.GlobalConst
import _RedGold__.main.commands.user.shop.listeners.goldShop.GoldGlobalConst
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class FoodListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is FoodHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return
        
        val player = event.whoClicked as Player
        val clickType = event.click
        val slot = event.slot

        if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
            val itemTimes = GlobalConst.setItemTimes(clickType)
            
            when (slot) {
                10 -> FoodConst.buy(player, 0, itemTimes)
                11 -> FoodConst.buy(player, 1, itemTimes)
                12 -> FoodConst.buy(player, 2, itemTimes)
                13 -> FoodConst.buy(player, 3, itemTimes)
                14 -> FoodConst.buy(player, 4, itemTimes)
                15 -> FoodConst.buy(player, 5, itemTimes)
                16 -> FoodConst.buy(player, 6, itemTimes)

                19 -> FoodConst.buy(player, 7, itemTimes)
                20 -> FoodConst.buy(player, 8, itemTimes)
                21 -> FoodConst.buy(player, 9, itemTimes)
                22 -> FoodConst.buy(player, 10, itemTimes)
                23 -> FoodConst.buy(player, 11, itemTimes)
                24 -> FoodConst.buy(player, 12, itemTimes)
                25 -> FoodConst.buy(player, 13, itemTimes)
            }
        }
    }
}