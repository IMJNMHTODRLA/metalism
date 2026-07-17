package _RedGold__.main.commands.user.shop.listeners.monthlyShop

import _RedGold__.main.commands.user.shop.listeners.GlobalConst
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.PREFIX
import _RedGold__.main.managers.playerData.data
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.time.LocalDate

object MonthlyConst {
    private val itemDataList = listOf(
        GlobalConst.ShopItem(Material.ELYTRA, "겉날개", 27_000_000),
        GlobalConst.ShopItem(Material.NETHER_STAR, "네더의 별", 38_000_000),
        GlobalConst.ShopItem(Material.NAME_TAG, "이름표", 10_000_000),
        //GlobalConst.ShopItem(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, "대장장이 형판", 8_000_000),
        //GlobalConst.ShopItem(Material.SPONGE, "스펀지", 650_000)
    )

    private val currentItemIndex = (LocalDate.now().monthValue - 1) % itemDataList.size

    fun buy(player: Player) {
        val item = itemDataList.getOrNull(currentItemIndex)?: return

        if (player.data.shopData.isPurchaseMonthly) {
            player.fail("&c더 이상 구매할 수 없습니다. 다음 달에 다시 구매해주세요.")
            return
        }

        val totalItemPrice = item.buy?: return
        if (player.data.gold < totalItemPrice) {
            player.fail("&c골드가 부족합니다. 필요 골드: ${(totalItemPrice - player.data.gold).toFormat()} 골드")
            return
        }

        if (!player.inv.hasSpace(item.id)) {
            player.fail("&c인벤토리 공간이 부족합니다.")
            return
        }

        player.data.shopData.isPurchaseMonthly = true
        player.data.gold -= totalItemPrice

        player.inv += ItemStack(item.id)
        player.good("&a${item.name}을(를) 구매했습니다.")
    }

    fun setShopItem(): ItemStack {
        val item = itemDataList.getOrNull(currentItemIndex)?: return BACKGROUND
        item.buy?: return BACKGROUND

        return getItem(
            item.id,
            "&f&l${item.name}",
            listOf(
                "",
                PREFIX,
                "",
                "&a&l[구매(좌클릭)] &f&l구매가: ${item.buy.toFormat()} 골드",
                "",
            )
        )
    }
}