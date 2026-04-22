package _RedGold__.main.commands.user.shop.listeners.goldShop.mineralGui

import _RedGold__.main.commands.user.shop.listeners.GlobalConst
import _RedGold__.main.commands.user.shop.listeners.goldShop.GoldGlobalConst
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class MineralListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is MineralHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val clickType = event.click
        val slot = event.slot

        if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
            val itemTimes = GlobalConst.setItemTimes(clickType)

            when (slot) {
                10 -> MineralConst.buy(player, 0, itemTimes)
                11 -> MineralConst.buy(player, 1, itemTimes)
                12 -> MineralConst.buy(player, 2, itemTimes)
                13 -> MineralConst.buy(player, 3, itemTimes)
                14 -> MineralConst.buy(player, 4, itemTimes)
                15 -> MineralConst.buy(player, 5, itemTimes)
                16 -> MineralConst.buy(player, 6, itemTimes)

                19 -> MineralConst.buy(player, 7, itemTimes)
                20 -> MineralConst.buy(player, 8, itemTimes)
                21 -> MineralConst.buy(player, 9, itemTimes)
                22 -> MineralConst.buy(player, 10, itemTimes)
                23 -> MineralConst.buy(player, 11, itemTimes)
                24 -> MineralConst.buy(player, 12, itemTimes)
                25 -> MineralConst.buy(player, 13, itemTimes)
            }
            return
        }

        if (clickType == ClickType.RIGHT || clickType == ClickType.SHIFT_RIGHT) {
            val itemTimes = GlobalConst.setItemTimes(clickType)

            when (slot) {
                10 -> MineralConst.sell(player, 0, itemTimes)
                11 -> MineralConst.sell(player, 1, itemTimes)
                12 -> MineralConst.sell(player, 2, itemTimes)
                13 -> MineralConst.sell(player, 3, itemTimes)
                14 -> MineralConst.sell(player, 4, itemTimes)
                15 -> MineralConst.sell(player, 5, itemTimes)
                16 -> MineralConst.sell(player, 6, itemTimes)

                19 -> MineralConst.sell(player, 7, itemTimes)
                20 -> MineralConst.sell(player, 8, itemTimes)
                21 -> MineralConst.sell(player, 9, itemTimes)
                22 -> MineralConst.sell(player, 10, itemTimes)
                23 -> MineralConst.sell(player, 11, itemTimes)
                24 -> MineralConst.sell(player, 12, itemTimes)
                25 -> MineralConst.sell(player, 13, itemTimes)
            }
        }
    }
}