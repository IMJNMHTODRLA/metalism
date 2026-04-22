package _RedGold__.main.commands.user.shop.listeners

import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.managers.playerData.PREFIX
import org.bukkit.Material
import org.bukkit.event.inventory.ClickType

object GlobalConst {
    data class ShopItem<T>(
        val id: T,
        val name: String,
        val buy: Int? = null,
        val sell: Int? = null
    )

    val setItemTimes = { clickType: ClickType ->
        if (clickType == ClickType.SHIFT_LEFT || clickType == ClickType.SHIFT_RIGHT) 64 else 1
    }

    val noSaleItem = getItem(
        Material.BARRIER,
        "&c&l판매하고 있는 아이템이 아닙니다.",
        listOf("", PREFIX, "&c&l[구매 불가]", "", "&c&l[판매 불가]", "")
    )
}