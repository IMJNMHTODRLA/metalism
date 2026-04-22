package _RedGold__.main.commands.user.shop.listeners.userShop.userItemBuyGui

import _RedGold__.main.commands.user.shop.listeners.userShop.UserGlobalConst
import _RedGold__.main.commands.user.shop.listeners.userShop.userItemListGui.UserItemListGui
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.functions.Scheduler.taskAsync
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.functions.afterWith
import _RedGold__.main.functions.remainingWith
import _RedGold__.main.functions.toPercent
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.userShopManager.UserShopDetailData
import _RedGold__.main.managers.userShopManager.FEE_PERCENT
import _RedGold__.main.managers.userShopManager.getItemFromId
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class UserItemBuyGui(private val plugin: JavaPlugin) {
    fun openGui(player: Player, returnPage: Int, id: Int) {
        val uuid = player.uniqueId

        val cooldown = UserGlobalConst.dbCooldown[uuid]?: 0
        if (!cooldown.afterWith(UserGlobalConst.COOLDOWN_TIME)) {
            player.sendMsg(
                "&c${cooldown.remainingWith(UserGlobalConst.COOLDOWN_TIME)}초 후에 다시 시도해주세요."
            )
            UserItemListGui().openGui(player, returnPage)
            return
        }

        UserGlobalConst.dbCooldown[uuid] = now

        plugin.taskAsync {
            val itemData = getItemFromId(id)

            plugin.task {
                itemData?: run {
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