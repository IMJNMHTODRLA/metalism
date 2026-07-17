package _RedGold__.main.commands.user.shop.listeners.goldShop.cpvpGui

import _RedGold__.main.commands.user.shop.listeners.GlobalConst
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

object CpvpConst {
    private val itemDataList = listOf(
        GlobalConst.ShopItem(Material.END_CRYSTAL, "엔드 수정", 25_000), //엔드 수정
        GlobalConst.ShopItem(Material.OBSIDIAN, "흑요석", 8_000), //흑요석
        GlobalConst.ShopItem(Material.TOTEM_OF_UNDYING, "불사의 토템", 30_000), //불토
        GlobalConst.ShopItem(Material.GOLDEN_APPLE, "황금 사과", 12_500), //황금 사과
        GlobalConst.ShopItem(Material.ENDER_PEARL, "엔더 진주", 30_000), //엔더진주
        GlobalConst.ShopItem(Material.RESPAWN_ANCHOR, "리스폰 정박기", 25_000), //리스폰 정박기
        GlobalConst.ShopItem(Material.GLOWSTONE, "발광석", 8_000), //발광석
        GlobalConst.ShopItem(Material.EXPERIENCE_BOTTLE, "경험치 병", 8_000), //경험치 병
        GlobalConst.ShopItem(Material.ARROW, "화살", 4_000), //화살
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
            "&5&l${item.name}",
            listOf(
                "",
                PREFIX,
                "&a&l[구매(좌클릭)] &f&l구매가: ${item.buy.toFormat()} 골드",
                "&8Shift + 좌클릭 시 64개가 구매됩니다.",
                "",
                "&c&l[판매 불가]",
                ""
            )
        )
    }
}