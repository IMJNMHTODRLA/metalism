package _RedGold__.main.commands.user.shop.listeners.dailyShop.goldDailyGui

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

object GoldDailyConst {
    private val enhanceBase = DailyGlobalConst.range.random(Random)

    private val itemDataList = listOf(
        GlobalConst.ShopItem(Material.ENDER_EYE, "엔더의 눈", 6_000),
        GlobalConst.ShopItem(Material.FIREWORK_ROCKET, "폭죽 로켓", 4_000),
        GlobalConst.ShopItem(Material.BONE_BLOCK, "뼈 블록", 12_000),
        GlobalConst.ShopItem(Material.WIND_CHARGE, "돌풍구", 8_000),

        GlobalConst.ShopItem(
            ReinforceItemList[DailyGlobalConst.getSkillItem(enhanceBase, 0)], "일반 강화 아이템", 8_000
        ),
        GlobalConst.ShopItem(
            ReinforceItemList[DailyGlobalConst.getSkillItem(enhanceBase, 1)], "하급 강화 아이템", 12_000
        ),
        GlobalConst.ShopItem(
            ReinforceItemList[DailyGlobalConst.getSkillItem(enhanceBase, 2)], "초급 강화 아이템", 24_000
        ),
    )

    fun buy(player: Player, shopId: Int) {
        val item = itemDataList.getOrNull(shopId)?: return

        val totalPurchaseDaily = player.data.shopData.totalPurchaseDaily
        if (totalPurchaseDaily[DailyEnum.GOLD]?.contains(shopId) == true) {
            player.fail("&c더 이상 구매할 수 없습니다. 다음 날에 다시 구매해주세요.")
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

        totalPurchaseDaily.getOrPut(DailyEnum.GOLD) { mutableSetOf() }.add(shopId)
        player.data.gold -= totalItemPrice

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
                "&a&l[구매(좌클릭)] &f&l구매가: ${item.buy.toFormat()} 골드",
                "",
            )
        )
    }
}