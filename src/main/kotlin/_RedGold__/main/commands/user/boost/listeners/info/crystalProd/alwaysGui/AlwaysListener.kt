package _RedGold__.main.commands.user.boost.listeners.info.crystalProd.alwaysGui

import _RedGold__.main.commands.user.boost.listeners.info.InfoGlobalConst
import _RedGold__.main.commands.user.boost.listeners.info.crystalProd.crystalProdGui.CrystalProdGui
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.PlusMath.pow
import _RedGold__.main.functions.task
import _RedGold__.main.loads.RequireListener
import _RedGold__.main.managers.playerData.data
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent

@RequireListener
class AlwaysListener : Listener {
    @EventHandler
    fun onCloseInventory(event: InventoryCloseEvent) {
        val gui = event.inventory
        val player = event.player as Player
        if (gui.holder !is AlwaysHolder) return

        task(1) {
            if (!player.isOnline) return@task
            CrystalProdGui().openGui(player)
        }
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val gui = event.view.topInventory
        val holder = gui.holder as? AlwaysHolder?: return

        event.isCancelled = true
        if (event.clickedInventory != gui) return

        val player = event.whoClicked as Player
        when(
            val slot = event.slot
        ) {
            12 -> InfoGlobalConst.buy(
                player, "상시 판매 크리스탈", holder.getTotalPrice,
                { false }
            ) {
                data.crystal += holder.addCrystal
            }

            in 18..26 -> {
                val change = when(slot) {
                    in 18..21 -> -(AlwaysConst.DEFAULT_CRYSTAL * 10.pow(21 - slot)) // 차감은 음수로
                    in 23..26 -> AlwaysConst.DEFAULT_CRYSTAL * 10.pow(slot - 23) // 추가는 양수로
                    else -> return
                }
                val newAmount = holder.addCrystal + change

                if (newAmount < 0) {
                    player.fail("&c음수로 내릴 수 없습니다.")
                    return
                }

                if (newAmount >= AlwaysConst.MAX_ADD) {
                    player.fail("&c더 이상 높힐 수 없습니다.")
                    return
                }

                holder.addCrystal = newAmount

                gui.item[22] = getItem(
                    Material.GRAY_STAINED_GLASS_PANE,
                    "&b&l구매 할 크리스탈&f: &b&l${holder.addCrystal.toFormat()} 크리스탈"
                )

                gui.item[13] = getItem(
                    Material.DIAMOND,
                    "&e&l클릭하여 상시 크리스탈 구매하기",
                    "",
                    "&b&l구매 할 크리스탈&f: &b&l${holder.addCrystal.toFormat()} 크리스탈",
                    "&f&l구매가: &4&l${holder.getTotalPrice.toFormat()} 루비",
                    "",
                    "&8&o* 구매 제한 없음"
                )

                if (change < 0) player.sendSound(Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f)
                else player.sendSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP)
            }
        }
    }
}