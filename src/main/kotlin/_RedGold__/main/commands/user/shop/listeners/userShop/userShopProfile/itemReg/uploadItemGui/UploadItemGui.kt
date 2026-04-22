package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.itemReg.uploadItemGui

import _RedGold__.main.commands.user.shop.listeners.userShop.UserGlobalConst
import _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.ProfileGlobalConst
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.FastGui.end
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.BACKGROUND_1
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class UploadItemGui {
    fun openGui(player: Player, returnPage: Int, itemData: ItemStack? = null) {
        val uuid = player.uniqueId

        val newTotal = UserGlobalConst.newUserItemData[uuid]?.size?: 0
        val existingTotal = UserGlobalConst.allUserShopData.count { it.uuid == uuid && !it.isCollect && !it.isPurchase && !it.isDelete }
        val totalRegged = newTotal + existingTotal

        val maxRegged = ProfileGlobalConst.maxRegList(PermissionEnum[player])

        if (totalRegged > maxRegged) {
            player.fail("&c더 이상 등록을 못합니다.")
            return
        }

        val gui = UploadItemHolder(returnPage, itemData).inventory
        gui.item(BACKGROUND)
        gui.item[18..gui.end] = BACKGROUND_1

        gui.item[13] = getItem(
            Material.BLACK_STAINED_GLASS_PANE,
            "&e&아이템 클릭 시 해당 아이템이 이곳에 표시됩니다."
        )

        gui.item[18] = getItem(Material.RED_STAINED_GLASS_PANE, "&c&l취소").apply { enchantEffect() }
        gui.item[26] = getItem(Material.GREEN_STAINED_GLASS_PANE, "&a&l다음 단계로 진행하기")

        player.inv + gui
        player.sendSound(Sound.UI_BUTTON_CLICK)
    }
}