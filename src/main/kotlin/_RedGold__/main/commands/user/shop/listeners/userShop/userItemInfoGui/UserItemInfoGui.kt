package _RedGold__.main.commands.user.shop.listeners.userShop.userItemInfoGui

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
import _RedGold__.main.managers.playerData.PREFIX
import _RedGold__.main.managers.userShopManager.FEE_PERCENT
import _RedGold__.main.managers.userShopManager.getItemFromId
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class UserItemInfoGui(private val plugin: JavaPlugin) {
    fun openGui(player: Player, returnPage: Int, id: Int) {
        val gui = UserItemInfoHolder(returnPage).inventory
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
            (gui.holder as UserItemInfoHolder).itemData = itemData

            plugin.task {
                itemData?: run {
                    player.sendMsg("&c아이템을 찾을 수 없습니다.")
                    UserItemListGui().openGui(player, returnPage)
                    return@task
                }

                gui.item(BACKGROUND)

                gui.item[12] = itemData.item

                gui.item[14] = getItem(
                    Material.BOOK,
                    "&e&l상세 정보",
                    listOf("",
                        PREFIX,
                        "&f&l가격: ${itemData.priceType.color}${itemData.priceAmount.toFormat()} ${itemData.priceType.displayName}",
                        "&7&l- 거래 수수료(${FEE_PERCENT.toPercent}): ${(itemData.priceAmount * FEE_PERCENT).toFormat(0)}",
                        "&a&l[구매(좌클릭)]&f&l: 구매 확인 창으로 이동합니다."
                    )
                )

                player.inv + gui
                player.sendSound(Sound.BLOCK_NOTE_BLOCK_PLING, 2f)
            }
        }
    }
}