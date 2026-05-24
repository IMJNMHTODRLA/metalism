package _RedGold__.main.commands.user.shop.listeners.userShop.userItemListGui

import _RedGold__.main.functions.Gui.getItem
import org.bukkit.Material

object UserItemListConst {
    fun getIdFromN(page: Int, n: Int) = n + (page * 28)
    fun getSlot(n: Int) = n + 10 + (n / 7 * 2)
    fun getIdFromSlot(page: Int, slot: Int) =
        slot.takeIf { it in 10..43 && it % 9 in 1..7 }
            ?.let { (it - 10) - ((it - 10) / 9 * 2) + (page * 28) }
            ?: -1


    val isExpiredItem = getItem(
        Material.RED_STAINED_GLASS_PANE,
        "&c&l판매 기간이 지난 아이템입니다.",
        listOf("",
            "&c&l[구매 불가]",
            "&c&l[상세 정보 불가]"
        )
    )
}