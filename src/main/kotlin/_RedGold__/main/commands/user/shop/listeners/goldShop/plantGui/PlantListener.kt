package _RedGold__.main.commands.user.shop.listeners.goldShop.plantGui

import _RedGold__.main.commands.user.shop.listeners.GlobalConst
import _RedGold__.main.commands.user.shop.listeners.goldShop.GoldGlobalConst
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.data
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent

@RequireListener
class PlantListener : Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        if (gui.holder !is PlantHolder) return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        val clickType = event.click
        val slot = event.slot

        if (slot == 31) {
            val resetCrystal = GoldGlobalConst.RESET_CRYSTAL
            if (player.data.crystal < resetCrystal) {
                player.fail("&c크리스탈이 부족합니다. 필요 크리스탈: ${(resetCrystal - player.data.crystal).toFormat()} 크리스탈")
                return
            }

            player.data.shopData.totalPurchasePlant.clear()
            player.data.crystal -= resetCrystal
            player.good("&a판매 횟수 초기화가 완료 되었습니다.")

            PlantGui().openGui(player)
            return
        }

        if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
            val itemTimes = GlobalConst.setItemTimes(clickType)

            when (slot) {
                10 -> PlantConst.buy(player, 0, itemTimes)
                11 -> PlantConst.buy(player, 1, itemTimes)
                12 -> PlantConst.buy(player, 2, itemTimes)
                13 -> PlantConst.buy(player, 3, itemTimes)
                14 -> PlantConst.buy(player, 4, itemTimes)
                15 -> PlantConst.buy(player, 5, itemTimes)
                16 -> PlantConst.buy(player, 6, itemTimes)

                19 -> PlantConst.buy(player, 7, itemTimes)
                20 -> PlantConst.buy(player, 8, itemTimes)
                21 -> PlantConst.buy(player, 9, itemTimes)
                22 -> PlantConst.buy(player, 10, itemTimes)
                23 -> PlantConst.buy(player, 11, itemTimes)
                24 -> PlantConst.buy(player, 12, itemTimes)
                25 -> PlantConst.buy(player, 13, itemTimes)
            }
            return
        }

        if (clickType == ClickType.RIGHT || clickType == ClickType.SHIFT_RIGHT) {
            val itemTimes = GlobalConst.setItemTimes(clickType)

            when (slot) {
                10 -> PlantConst.sell(player, 0, itemTimes)
                11 -> PlantConst.sell(player, 1, itemTimes)
                12 -> PlantConst.sell(player, 2, itemTimes)
                13 -> PlantConst.sell(player, 3, itemTimes)
                14 -> PlantConst.sell(player, 4, itemTimes)
                15 -> PlantConst.sell(player, 5, itemTimes)
                16 -> PlantConst.sell(player, 6, itemTimes)

                19 -> PlantConst.sell(player, 7, itemTimes)
                20 -> PlantConst.sell(player, 8, itemTimes)
                21 -> PlantConst.sell(player, 9, itemTimes)
                22 -> PlantConst.sell(player, 10, itemTimes)
                23 -> PlantConst.sell(player, 11, itemTimes)
                24 -> PlantConst.sell(player, 12, itemTimes)
                25 -> PlantConst.sell(player, 13, itemTimes)
            }
        }
    }
}