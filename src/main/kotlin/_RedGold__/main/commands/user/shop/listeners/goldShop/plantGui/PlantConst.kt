package _RedGold__.main.commands.user.shop.listeners.goldShop.plantGui

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

object PlantConst {
    private val itemDataList = listOf(
        //구매, 판매
        GlobalConst.ShopItem(Material.WHEAT, "밀", sell = 1342),
        GlobalConst.ShopItem(Material.WHEAT_SEEDS, "밀 씨앗", 600, 120),
        GlobalConst.ShopItem(Material.BEETROOT, "비트", sell = 671),
        GlobalConst.ShopItem(Material.BEETROOT_SEEDS, "비트 씨앗", 300, 60),
        GlobalConst.ShopItem(Material.POTATO, "감자", 1952, 1183),
        GlobalConst.ShopItem(Material.POISONOUS_POTATO, "독이 든 감자", sell = 25_520),
        GlobalConst.ShopItem(Material.CARROT, "당근", 1952, 1342),

        GlobalConst.ShopItem(Material.NETHER_WART, "네더 사마귀", 3241, 2482),
        GlobalConst.ShopItem(Material.PUMPKIN, "호박", sell = 320),
        GlobalConst.ShopItem(Material.PUMPKIN_SEEDS, "호박씨", 700, 20),
        GlobalConst.ShopItem(Material.MELON_SLICE, "수박 조각", sell = 50),
        GlobalConst.ShopItem(Material.MELON_SEEDS, "수박씨", 700, 20),
        GlobalConst.ShopItem(Material.COCOA_BEANS, "코코아 콩", 1250, 110),
        GlobalConst.ShopItem(Material.SUGAR_CANE, "사탕수수", 1540, 75),
    )

    private val maxPurchaseList = listOf(
        128, 9999, //밀
        64, 9999, //비트
        128, //감자
        2, //독감자
        128, //당근
        128, //네더 사마귀
        64, 9999, //호박
        432, 9999, //수박
        48, //코코아 콩
        48, //사탕수수
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

        val totalPurchasePlant = player.data.shopData.totalPurchasePlant
        val totalPurchase = totalPurchasePlant[shopId]?: 0
        if (totalPurchase >= maxPurchase) {
            player.fail("&c더 이상 판매할 수 없습니다. 다음 날에 다시 판매해주세요.")
            return
        }

        if (!player.inv.hasAtLeast(item.id, itemTimes)) {
            player.fail("&c아이템이 부족합니다.")
            return
        }

        player.data.gold += item.sell * itemTimes
        totalPurchasePlant[shopId] = totalPurchase + itemTimes

        player.inv.safeRemoveItem(item.id, itemTimes)
        player.good("&a${item.name}을(를) ${itemTimes}개 판매하여 ${totalItemPrice.toFormat()} 골드를 얻었습니다.")

        PlantGui().openGui(player)
    }

    fun setShopItem(player: Player, shopId: Int): ItemStack {
        val item = itemDataList.getOrNull(shopId)?: return GlobalConst.noSaleItem

        val totalPurchase = player.data.shopData.totalPurchasePlant[shopId]?: 0
        val maxPurchase = maxPurchaseList.getOrNull(shopId)?: return GlobalConst.noSaleItem

        return getItem(
            item.id,
            "&f&l${item.name} &8판매 횟수: ($totalPurchase/$maxPurchase)",
            listOf(
                "",
                PREFIX,
                !item.buy.isNull then "&a&l[구매(좌클릭)] &f&l구매가: ${item.buy?.toFormat()} 골드" orElse "&c&l[구매 불가]",
                !item.buy.isNull then "&8Shift + 좌클릭 시 64개가 구매됩니다." orElse "",
                !item.buy.isNull then "&b&l[판매(우클릭)] &f&l구매가: ${item.sell?.toFormat()} 골드" orElse "&c&l[판매 불가]",
                !item.buy.isNull then "&8Shift + 우클릭 시 64개가 판매됩니다." orElse "",
            )
        )
    }
}