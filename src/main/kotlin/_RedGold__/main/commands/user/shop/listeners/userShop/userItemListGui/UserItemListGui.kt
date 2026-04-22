package _RedGold__.main.commands.user.shop.listeners.userShop.userItemListGui

import _RedGold__.main.commands.user.shop.listeners.userShop.UserGlobalConst
import _RedGold__.main.functions.FastBoolean.trueRun
import _RedGold__.main.functions.FastGui.enchantEffect
import _RedGold__.main.functions.FastGui.end
import _RedGold__.main.functions.FastGui.item
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.getPlayerSkull
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.isInit
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.BACKGROUND_1
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

class UserItemListGui {
    fun openGui(player: Player, page: Int) {
        val gui = UserItemListHolder(page).inventory

        UserGlobalConst::allUserShopData.isInit.trueRun { return }

        gui.item[0..44] = BACKGROUND
        gui.item[45..gui.end] = BACKGROUND_1

        repeat(28) { i ->
            val slot = UserItemListConst.getSlot(i)
            val getId = UserItemListConst.getIdFromN(slot, i)
            val previewData = UserGlobalConst.allUserShopData.getOrNull(getId)?: return@repeat

            (previewData.isCollect || previewData.isPurchase).trueRun {
                gui.item[slot] = UserItemListConst.isExpiredItem
                return@repeat
            }

            gui.item[slot] = getItem(
                previewData.displayMaterial,
                previewData.displayName,
                listOf(
                    "",
                    "&a&l[구매(좌클릭)]&f&l: 구매 확인 창으로 이동합니다.",
                    "&e&l[상세 정보(우클릭)]&f&l: 아이템 상세 정보를 확인합니다."
                ),
                previewData.displayAmount
            ).apply {
                if (previewData.displayIsEnchant) enchantEffect()
            }
        }

        gui.item[46] = getItem(
            Material.RED_STAINED_GLASS_PANE,
            "&c&l이전 페이지로 이동"
        )

        gui.item[48] = getItem(
            Material.BOOK,
            "&8&l현재 페이지: ($page)"
        )

        gui.item[50] = getPlayerSkull(
            player.uniqueId,
            "&e&l클릭 시 유저 상점 프로필로 이동됩니다.",
        )

        gui.item[53] = getItem(
            Material.GREEN_STAINED_GLASS_PANE,
            "&a&l다음 페이지로 이동"
        )

        player.inv + gui
        player.sendSound(Sound.UI_BUTTON_CLICK)
    }
}