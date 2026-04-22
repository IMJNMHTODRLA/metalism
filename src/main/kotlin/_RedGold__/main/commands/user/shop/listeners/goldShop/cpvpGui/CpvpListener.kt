package _RedGold__.main.commands.user.shop.listeners.goldShop.cpvpGui

import _RedGold__.main.commands.user.shop.listeners.GlobalConst
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class CpvpListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is CpvpHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val clickType = event.click
        val slot = event.slot

        if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
            val itemTimes = GlobalConst.setItemTimes(clickType)

            when (slot) {
                10 -> CpvpConst.buy(player, 0, itemTimes)
                11 -> CpvpConst.buy(player, 1, itemTimes)
                12 -> CpvpConst.buy(player, 2, itemTimes)
                13 -> CpvpConst.buy(player, 3, itemTimes)
                14 -> CpvpConst.buy(player, 4, itemTimes)
                15 -> CpvpConst.buy(player, 5, itemTimes)
                16 -> CpvpConst.buy(player, 6, itemTimes)

                19 -> CpvpConst.buy(player, 7, itemTimes)
                20 -> CpvpConst.buy(player, 8, itemTimes)
                21 -> CpvpConst.buy(player, 9, itemTimes)
                22 -> CpvpConst.buy(player, 10, itemTimes)
                23 -> CpvpConst.buy(player, 11, itemTimes)
                24 -> CpvpConst.buy(player, 12, itemTimes)
                25 -> CpvpConst.buy(player, 13, itemTimes)
            }
        }
    }
}