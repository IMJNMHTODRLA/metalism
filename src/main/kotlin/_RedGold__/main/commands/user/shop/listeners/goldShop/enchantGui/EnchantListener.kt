package _RedGold__.main.commands.user.shop.listeners.goldShop.enchantGui

import _RedGold__.main.commands.user.shop.listeners.GlobalConst
import _RedGold__.main.commands.user.shop.listeners.goldShop.GoldGlobalConst
import _RedGold__.main.loads.RequireListener
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class EnchantListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is EnchantHolder) return

        event.isCancelled = true

        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val holder = gui.holder as EnchantHolder
        val clickType = event.click
        val slot = event.slot
        val page = holder.page

        when(slot) {
            27 -> if (holder.page > 1) EnchantGui().openGui(player, page - 1)
            35 -> if (holder.page < EnchantConst.MAX_PAGE) EnchantGui().openGui(player, page + 1)
        }

        if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
            val itemTimes = GlobalConst.setItemTimes(clickType)
            val buy = EnchantConst::buy
            val pageFormulas = EnchantConst.pageFormulas

            when (slot) {
                10 -> buy(player, pageFormulas(page, 0), itemTimes)
                11 -> buy(player, pageFormulas(page, 1), itemTimes)
                12 -> buy(player, pageFormulas(page, 2), itemTimes)
                13 -> buy(player, pageFormulas(page, 3), itemTimes)
                14 -> buy(player, pageFormulas(page, 4), itemTimes)
                15 -> buy(player, pageFormulas(page, 5), itemTimes)
                16 -> buy(player, pageFormulas(page, 6), itemTimes)

                19 -> buy(player, pageFormulas(page, 7), itemTimes)
                20 -> buy(player, pageFormulas(page, 8), itemTimes)
                21 -> buy(player, pageFormulas(page, 9), itemTimes)
                22 -> buy(player, pageFormulas(page, 10), itemTimes)
                23 -> buy(player, pageFormulas(page, 11), itemTimes)
                24 -> buy(player, pageFormulas(page, 12), itemTimes)
                25 -> buy(player, pageFormulas(page, 13), itemTimes)
            }
        }
    }
}