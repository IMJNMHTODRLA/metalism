package _RedGold__.main.commands.user.shop.listeners.dailyShop.crystalDailyGui

import _RedGold__.main.commands.user.shop.listeners.GlobalConst
import _RedGold__.main.commands.user.shop.listeners.dailyShop.DailyGlobalConst
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.managers.playerData.BACKGROUND
import _RedGold__.main.managers.playerData.PREFIX
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.variableManager.DailyEnum
import _RedGold__.main.managers.reinforceManager.ReinforceItemList
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

object CrystalDailyConst {
    private val enhanceBase = DailyGlobalConst.range.random(Random)

    private val itemDataList = listOf(
        GlobalConst.ShopItem(Material.GOLD_INGOT, "2,000,000 골드", 120),
        GlobalConst.ShopItem(Material.GOLD_BLOCK, "3,000,000 골드", 200),

        GlobalConst.ShopItem(
            ReinforceItemList[DailyGlobalConst.getSkillItem(enhanceBase, 1)], "하급 강화 아이템", 30
        ),
        GlobalConst.ShopItem(
            ReinforceItemList[DailyGlobalConst.getSkillItem(enhanceBase, 2)], "초급 강화 아이템", 90
        ),
        GlobalConst.ShopItem(
            ReinforceItemList[DailyGlobalConst.getSkillItem(enhanceBase, 3)], "중급 강화 아이템", 360
        ),
    )

    fun buy(player: Player, shopId: Int) {
        val item = itemDataList.getOrNull(shopId)?: return

        val totalPurchaseDaily = player.data.shopData.totalPurchaseDaily
        if (totalPurchaseDaily[DailyEnum.CRYSTAL]?.contains(shopId) == true) {
            player.fail("&c더 이상 구매할 수 없습니다. 다음 날에 다시 구매해주세요.")
            return
        }

        val totalItemPrice = item.buy?: return
        if (player.data.crystal < totalItemPrice) {
            player.fail("&c크리스탈이 부족합니다. 필요 크리스탈: ${(totalItemPrice - player.data.crystal).toFormat()} 크리스탈")
            return
        }

        if (!player.inv.hasSpace(item.id)) {
            player.fail("&c인벤토리 공간이 부족합니다.")
            return
        }

        totalPurchaseDaily.getOrPut(DailyEnum.CRYSTAL) { mutableSetOf() }.add(shopId)
        player.data.crystal -= totalItemPrice

        player.inv += ItemStack(item.id)
        player.good("&a${item.name}을(를) 구매했습니다.")
    }

    fun setShopItem(shopId: Int): ItemStack {
        val item = itemDataList.getOrNull(shopId)?: return BACKGROUND
        item.buy?: return BACKGROUND

        return getItem(
            item.id,
            "&f&l${item.name}",
            listOf(
                "",
                PREFIX,
                "&a&l[구매(좌클릭)] &f&l구매가: ${item.buy.toFormat()} 크리스탈",
                "",
            )
        )
    }
}