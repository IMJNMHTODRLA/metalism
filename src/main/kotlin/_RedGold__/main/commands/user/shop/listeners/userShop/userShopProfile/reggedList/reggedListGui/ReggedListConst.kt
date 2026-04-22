package _RedGold__.main.commands.user.shop.listeners.userShop.userShopProfile.reggedList.reggedListGui

import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.managers.userShopManager.UserShopInfoData
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ReggedListConst {
    val unReggedItem = getItem(
        Material.BLACK_STAINED_GLASS_PANE,
        "&c&l아이템을 등록하지 않았습니다.",
        listOf("",
            "&e&l클릭하여 등록하기"
        )
    )

    val prohRegItem = getItem(
        Material.BARRIER,
        "&c&l아이템 등록이 불가능합니다.",
        listOf("",
            "&e&l등록을 원할 경우 상위 랭크가 필요합니다."
        )
    )

    fun collectRegItem(data: UserShopInfoData): ItemStack {
        return getItem(
            data.displayMaterial,
            data.displayName,
            listOf("",
                "&a&l[회수(좌클릭)]&f&l: 회수 확인 창으로 이동합니다.",
                "&e&l[상세 정보(우클릭)]&f&l: 아이템 상세 정보를 확인합니다."
            )
        )
    }
}