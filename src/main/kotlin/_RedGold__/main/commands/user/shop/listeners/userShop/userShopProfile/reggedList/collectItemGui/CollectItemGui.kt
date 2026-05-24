package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.collectItemGui

import _RedGold__.main.commands.user.shop.listeners.userShop.UserGlobalConst
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.ListGlobalConst
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.FastBoolean.trueRun
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.task
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class CollectItemGui {
    fun openGui(player: Player, returnPage: Int, data: Any) {
        UserGlobalConst.isUnderCooldown(player).trueRun { return }

        val gui = CollectItemHolder(returnPage).inventory

        gui.item(BACKGROUND)
        gui.item[11] = getItem(Material.GREEN_CONCRETE, "&a&l회수 하기")
        gui.item[15] = getItem(Material.RED_CONCRETE, "&c&l취소")

        taskAsync {
            val detail = ListGlobalConst.getDBDetail(data)
            (gui.holder as CollectItemHolder).detail = detail

            task {
                gui.item[13] = detail?.item ?: run {
                    player.sendMsg("&c아이템을 찾을 수 없습니다.")
                    return@task
                }

                player.inv + gui
                player.sendSound(Sound.UI_BUTTON_CLICK)
            }
        }
    }
}