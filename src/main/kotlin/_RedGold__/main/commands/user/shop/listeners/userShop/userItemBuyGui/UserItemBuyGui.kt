package _RedGold__.main.commands.user.shop.listeners.userShop.userItemBuyGui

import _RedGold__.main.commands.user.shop.listeners.userShop.UserGlobalConst
import _RedGold__.main.commands.user.shop.listeners.userShop.userItemListGui.UserItemListGui
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.FastBoolean.trueRun
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.task
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.functions.toPercent
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.userShopManager.FEE_PERCENT
import _RedGold__.main.managers.userShopManager.UserShopDetailData
import _RedGold__.main.managers.userShopManager.getItemFromId
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class UserItemBuyGui {
    fun openGui(player: Player, returnPage: Int, id: Int) {
        UserGlobalConst.isUnderCooldown(player).trueRun { return }

        taskAsync {
            val itemData = getItemFromId(id)

            task {
                itemData ?: run {
                    player.sendMsg("&c아이템을 찾을 수 없습니다.")
                    UserItemListGui().openGui(player, returnPage)
                    return@task
                }
                openGui(player, returnPage, itemData)
            }
        }
    }

    fun openGui(player: Player, returnPage: Int, itemData: UserShopDetailData) {
        val gui = UserItemBuyHolder(returnPage, itemData).inventory
        gui.item(BACKGROUND)

        gui.item[13] = itemData.item

        val fee = (itemData.priceAmount * FEE_PERCENT).toInt()
        gui.item[11] = getItem(
            Material.GREEN_CONCRETE,
            "&a&l구매",
            listOf("",
                "&7&l- 거래 수수료(${FEE_PERCENT.toPercent}): ${fee.toFormat()}",
                "&f&l총 가격: ${itemData.priceType.color}${(itemData.priceAmount + fee).toFormat()} ${itemData.priceType.displayName}",
            )
        )

        gui.item[15] = getItem(
            Material.RED_CONCRETE,
            "&c&l취소",
        )

        player.inv + gui
        player.sendSound(Sound.ENTITY_PLAYER_LEVELUP)
    }
}