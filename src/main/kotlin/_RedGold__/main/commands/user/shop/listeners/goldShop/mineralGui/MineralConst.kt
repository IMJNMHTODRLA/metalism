package _RedGold__.main.commands.user.shop.listeners.goldShop.mineralGui

import _RedGold__.main.commands.user.shop.listeners.GlobalConst
import _RedGold__.main.commands.user.shop.listeners.goldShop.GoldGlobalConst
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.Cubic.orElse
import _RedGold__.main.functions.Cubic.then
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.isNull
import _RedGold__.main.managers.playerData.PREFIX
import _RedGold__.main.managers.playerData.data
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object MineralConst {
    private val itemDataList = listOf(
        //구매, 판매
        GlobalConst.ShopItem(Material.COAL, "석탄", 1000, 800),
        GlobalConst.ShopItem(Material.RAW_COPPER, "구리 원석", 800, 600),
        GlobalConst.ShopItem(Material.COPPER_INGOT, "구리 주괴", 1000, 800),
        GlobalConst.ShopItem(Material.RAW_IRON, "철 원석", 1200, 1000),
        GlobalConst.ShopItem(Material.IRON_INGOT, "철 주괴", 1400, 1200),
        GlobalConst.ShopItem(Material.RAW_GOLD, "금 원석", 1200, 1000),
        GlobalConst.ShopItem(Material.GOLD_INGOT, "금 주괴", 1400, 1200),

        GlobalConst.ShopItem(Material.LAPIS_LAZULI, "청금석", 800, 600),
        GlobalConst.ShopItem(Material.REDSTONE, "레드스톤", 800, 600),
        GlobalConst.ShopItem(Material.DIAMOND, "다이아몬드", 4800, 2800),
        GlobalConst.ShopItem(Material.EMERALD, "에메랄드", 3600, 1600),
        GlobalConst.ShopItem(Material.NETHERITE_SCRAP, "네더라이트 파편", 25_000, 6_000),
        GlobalConst.ShopItem(Material.NETHERITE_INGOT, "네더라이트 주괴", 80_000, 35_000),
        GlobalConst.ShopItem(Material.QUARTZ, "석영", 200, 100),
    )

    private val maxPurchaseList = listOf(
        64, //석탄
        32, //구리 원석
        64, //구리 주괴
        96, //철 원석
        128, //철 주괴
        96, //금 원석
        128, //금 주괴

        96, //청금석
        96, //레드스톤
        192, //다이아몬드
        192, //에메랄드
        32, //네더라이트 파편
        16, //네더라이트 주괴
        9999, //석영
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

        player.data.gold -= totalItemPrice
        player.inv.safeAddItem(item.id, itemTimes)
        player.good("&a${item.name}을(를) ${itemTimes}개 구매했습니다.")
    }

    fun sell(player: Player, shopId: Int, itemTimes: Int) {
        val item = itemDataList.getOrNull(shopId)?: return
        val maxPurchase = maxPurchaseList.getOrNull(shopId)?: return

        item.sell?: return
        val totalItemPrice = item.sell * itemTimes

        val totalPurchaseMineral = player.data.shopData.totalPurchaseMineral
        val totalPurchase = totalPurchaseMineral[shopId]?: 0
        if (totalPurchase >= maxPurchase) {
            player.fail("&c더 이상 판매할 수 없습니다. 다음 날에 다시 판매해주세요.")
            return
        }

        if (!player.inv.hasAtLeast(item.id, itemTimes)) {
            player.fail("&c아이템이 부족합니다.")
            return
        }

        player.data.gold += item.sell * itemTimes
        totalPurchaseMineral[shopId] = totalPurchase + itemTimes

        player.inv.safeRemoveItem(item.id, itemTimes)
        player.good("&a${item.name}을(를) ${itemTimes}개 판매하여 ${totalItemPrice.toFormat()} 골드를 얻었습니다.")

        MineralGui().openGui(player)
    }

    fun setShopItem(player: Player, shopId: Int): ItemStack {
        val item = itemDataList.getOrNull(shopId)?: return GlobalConst.noSaleItem

        val totalPurchase = player.data.shopData.totalPurchaseMineral[shopId]?: 0
        val maxPurchase = maxPurchaseList.getOrNull(shopId)?: return GlobalConst.noSaleItem

        return getItem(
            item.id,
            "&f&l${item.name} &8판매 횟수: ($totalPurchase/$maxPurchase)",
            listOf(
                "",
                PREFIX,
                "",
                !item.buy.isNull then "&a&l[구매(좌클릭)] &f&l구매가: ${item.buy?.toFormat()} 골드" orElse "&c&l[구매 불가]",
                !item.buy.isNull then "&8Shift + 좌클릭 시 64개가 구매됩니다." orElse "",
                !item.buy.isNull then "&b&l[판매(우클릭)] &f&l구매가: ${item.sell?.toFormat()} 골드" orElse "&c&l[판매 불가]",
                !item.buy.isNull then "&8Shift + 우클릭 시 64개가 판매됩니다." orElse "",
            )
        )
    }
}