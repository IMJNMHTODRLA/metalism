package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.reggedListGui

import _RedGold__.main.commands.user.shop.listeners.userShop.UserGlobalConst
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.ProfileGlobalConst
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.userShopManager.UserShopEntry
import _RedGold__.main.managers.userShopManager.UserShopInfoData
import org.bukkit.entity.Player

class ReggedListGui {
    fun openGui(player: Player, returnPage: Int) {
        val uuid = player.uniqueId

        val newReggedItems = UserGlobalConst.newUserItemData[uuid]?: mutableListOf()
        val preReggedItems = UserGlobalConst.allUserShopData
            .filter { it.uuid == uuid && !it.isDelete && !it.isPurchase}

        val gui = ReggedListHolder(returnPage, newReggedItems, preReggedItems).inventory
        val allItems = newReggedItems + preReggedItems

        val playerMaxReg = ProfileGlobalConst.maxRegList(PermissionEnum[player])

        repeat(ProfileGlobalConst.TOTAL_MAX_REG) { i ->
            gui.item[i] = when {
                i >= playerMaxReg -> ReggedListConst.prohRegItem

                i < allItems.size -> {
                    when (
                        val data = allItems[i]
                    ) {
                        is UserShopEntry -> ReggedListConst.collectRegItem(data.info)
                        is UserShopInfoData -> ReggedListConst.collectRegItem(data)
                        else -> ReggedListConst.unReggedItem
                    }
                }

                else -> ReggedListConst.unReggedItem
            }
        }
    }
}