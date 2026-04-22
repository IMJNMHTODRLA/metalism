package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.infoItemGui

import _RedGold__.main.commands.user.shop.listeners.userShop.UserGlobalConst
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.ListGlobalConst
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.FastBoolean.trueRun
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.functions.Scheduler.taskAsync
import _RedGold__.main.managers.playerData.BACKGROUND
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class InfoItemGui(private val plugin: JavaPlugin) {
    fun openGui(player: Player, returnPage: Int, data: Any) {
        UserGlobalConst.isUnderCooldown(player).trueRun { return }

        val gui = InfoItemHolder(returnPage).inventory
        gui.item(BACKGROUND)

        plugin.taskAsync {
            val detail = ListGlobalConst.getDBDetail(data)
            (gui.holder as InfoItemHolder).detail = detail

            plugin.task {
                gui.item[13] = detail?.item?: run {
                    player.sendMsg("&c아이템을 찾을 수 없습니다.")
                    return@task
                }

                player.inv + gui
                player.sendSound(Sound.UI_BUTTON_CLICK)
            }
        }
    }
}