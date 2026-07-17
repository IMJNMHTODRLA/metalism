package _RedGold__.main.commands.user.shop.listeners.goldShop.foodGui

import _RedGold__.main.commands.user.shop.listeners.GlobalConst
import _RedGold__.main.commands.user.shop.listeners.goldShop.GoldGlobalConst
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.managers.playerData.PREFIX
import _RedGold__.main.managers.playerData.data
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object FoodConst {
    private val itemDataList = listOf(
        GlobalConst.ShopItem(Material.GOLDEN_CARROT, "황금 당근", 15_500),
        GlobalConst.ShopItem(Material.COOKED_BEEF, "스테이크", 15_500),
        GlobalConst.ShopItem(Material.COOKED_PORKCHOP, "익힌 돼지고기", 14_000),
        GlobalConst.ShopItem(Material.COOKED_CHICKEN, "익힌 닭고기", 12_500),
        GlobalConst.ShopItem(Material.BREAD, "빵", 11_000),
        GlobalConst.ShopItem(Material.COOKIE, "쿠키", 10_000),
    )

    fun buy(player: Player, shopId: Int, itemTimes: Int) {
        val item = itemDataList.getOrNull(shopId)?: return
        item.buy?: return

        val totalItemPrice = item.buy * itemTimes
        if (player.data.gold < totalItemPrice) {
            player.fail("&c골드가 부족합니다. 필요 골드: ${(totalItemPrice - player.data.gold).toFormat()} 골드")
            return
        }

        if (!player.inv.hasSpace(item.id, itemTimes)) {
            player.fail("&c인벤토리 공간이 부족합니다.")
            return
        }

        player.inv.safeAddItem(item.id, itemTimes)
        player.data.gold -= totalItemPrice
        player.good("&a${item.name}을(를) ${itemTimes}개 구매했습니다.")
    }

    fun setShopItem(shopId: Int): ItemStack {
        val item = itemDataList.getOrNull(shopId)?: return GlobalConst.noSaleItem
        item.buy?: return GlobalConst.noSaleItem

        return getItem(
            item.id,
            "&f&l${item.name}",
            listOf(
                "",
                PREFIX,
                "",
                "&a&l[구매(좌클릭)] &f&l구매가: ${item.buy.toFormat()} 골드",
                "&8Shift + 좌클릭 시 64개가 구매됩니다.",
                "",
                "&c&l[판매 불가]",
                ""
            )
        )
    }
}