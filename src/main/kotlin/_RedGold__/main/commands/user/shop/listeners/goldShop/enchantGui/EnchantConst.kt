package _RedGold__.main.commands.user.shop.listeners.goldShop.enchantGui

import _RedGold__.main.commands.user.shop.listeners.GlobalConst
import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.functions.Gui.inv
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.modifyMeta
import _RedGold__.main.managers.playerData.PREFIX
import _RedGold__.main.managers.playerData.data
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta

object EnchantConst {
    const val MAX_PAGE = 3
    val pageFormulas = { page: Int, shopId: Int ->
        (page - 1) * 14 + shopId
    }
    private val itemDataList = listOf(
        //
        // 특별하고 ㅈㄴ 중요한거: 3_500_000
        // 개좋은: 3_000_000
        // 좋은: 2_750_000
        // 평범: 2_500_000
        // 별로: 2_250_000
        // 쓰래기: 2_000_000
        //
        GlobalConst.ShopItem(Enchantment.AQUA_AFFINITY, "친수성", 2_750_000),
        GlobalConst.ShopItem(Enchantment.BANE_OF_ARTHROPODS, "살충 V", 2_250_000),
        GlobalConst.ShopItem(Enchantment.BLAST_PROTECTION, "폭발로부터 보호 IV", 2_750_000),
        GlobalConst.ShopItem(Enchantment.BREACH, "격파 IV", 2_750_000),
        GlobalConst.ShopItem(Enchantment.CHANNELING, "집전", 2_750_000),
        GlobalConst.ShopItem(Enchantment.DENSITY, "육중 V", 2_750_000),
        GlobalConst.ShopItem(Enchantment.DEPTH_STRIDER, "물갈퀴 III", 3_000_000),
        GlobalConst.ShopItem(Enchantment.EFFICIENCY, "효율 V", 3_000_000),
        GlobalConst.ShopItem(Enchantment.FEATHER_FALLING, "가벼운 착지 IV", 3_000_000),
        GlobalConst.ShopItem(Enchantment.FIRE_ASPECT, "발화 II", 3_000_000),
        GlobalConst.ShopItem(Enchantment.FIRE_PROTECTION, "화염으로부터 보호 IV", 2_500_000),
        GlobalConst.ShopItem(Enchantment.FLAME, "화염", 2_750_000),
        GlobalConst.ShopItem(Enchantment.FORTUNE, "행운 III", 3_000_000),
        GlobalConst.ShopItem(Enchantment.FROST_WALKER, "차가운 걸음 II", 2_750_000),
        //1 페이지

        GlobalConst.ShopItem(Enchantment.IMPALING, "찌르기 V", 3_000_000),
        GlobalConst.ShopItem(Enchantment.INFINITY, "무한", 2_750_000),
        GlobalConst.ShopItem(Enchantment.KNOCKBACK, "밀치기 II", 2_250_000),
        GlobalConst.ShopItem(Enchantment.LOOTING, "약탈 III", 3_000_000),
        GlobalConst.ShopItem(Enchantment.LOYALTY, "충성 III", 3_000_000),
        GlobalConst.ShopItem(Enchantment.LUCK_OF_THE_SEA, "바다의 행운 III", 2_000_000),
        GlobalConst.ShopItem(Enchantment.LURE, "미끼 III", 2_000_000),
        GlobalConst.ShopItem(Enchantment.MENDING, "수선", 3_500_000),
        GlobalConst.ShopItem(Enchantment.MULTISHOT, "다중 발사", 2_500_000),
        GlobalConst.ShopItem(Enchantment.PIERCING, "관통 IV", 2_500_000),
        GlobalConst.ShopItem(Enchantment.POWER, "힘 V", 2_750_000),
        GlobalConst.ShopItem(Enchantment.PROJECTILE_PROTECTION, "발사체로부터 보호 IV", 2_500_000),
        GlobalConst.ShopItem(Enchantment.PROTECTION, "보호 IV", 3_000_000),
        GlobalConst.ShopItem(Enchantment.PUNCH, "밀어내기 II", 2_500_000),
        //2 페이지

        GlobalConst.ShopItem(Enchantment.QUICK_CHARGE, "빠른 장전 III", 2_500_000),
        GlobalConst.ShopItem(Enchantment.RESPIRATION, "호흡 III", 3_000_000),
        GlobalConst.ShopItem(Enchantment.RIPTIDE, "급류 III", 3_000_000),
        GlobalConst.ShopItem(Enchantment.SHARPNESS, "날카로움 V", 3_000_000),
        GlobalConst.ShopItem(Enchantment.SILK_TOUCH, "섬세한 손길", 2_750_000),
        GlobalConst.ShopItem(Enchantment.SMITE, "강타 V", 3_000_000),
        GlobalConst.ShopItem(Enchantment.SOUL_SPEED, "영혼 가속 III", 3_000_000),
        GlobalConst.ShopItem(Enchantment.SWEEPING_EDGE, "휩쓸기 III", 3_000_000),
        GlobalConst.ShopItem(Enchantment.SWIFT_SNEAK, "신속한 잠행 III", 3_000_000),
        GlobalConst.ShopItem(Enchantment.THORNS, "가시 III", 3_000_000),
        GlobalConst.ShopItem(Enchantment.UNBREAKING, "내구성 III", 3_000_000),
        GlobalConst.ShopItem(Enchantment.WIND_BURST, "돌풍 III", 3_000_000),
        //3페이지
    )

    fun buy(player: Player, shopId: Int, itemTimes: Int) {
        val enchant = itemDataList.getOrNull(shopId)?: return
        enchant.buy?: return
        val totalItemPrice = enchant.buy * itemTimes

        val itemId = Material.ENCHANTED_BOOK
        val item = ItemStack(itemId)
            .modifyMeta<EnchantmentStorageMeta> {
                addStoredEnchant(enchant.id, enchant.id.maxLevel, false)
            }

        if (player.data.gold < totalItemPrice) {
            player.fail("&c골드가 부족합니다. 필요 골드: ${(totalItemPrice - player.data.gold).toFormat()} 골드")
            return
        }

        if (!player.inv.hasSpace(itemId, itemTimes)) {
            player.fail("&c인벤토리 공간이 부족합니다.")
            return
        }

        repeat(itemTimes) { player.inv += item }

        player.data.gold -= totalItemPrice
        player.good("&a${enchant.id}을(를) ${itemTimes}개 구매했습니다.")
    }

    fun setShopItem(shopId: Int): ItemStack {
        val enchant = itemDataList.getOrNull(shopId)?: return GlobalConst.noSaleItem
        enchant.buy?: return GlobalConst.noSaleItem

        return getItem(
            Material.ENCHANTED_BOOK,
            "&b&l${enchant.name}",
            "",
            PREFIX,
            "&a&l[구매(좌클릭)] &f&l구매가: ${enchant.buy.toFormat()} 골드",
            "&8Shift + 좌클릭 시 64개가 구매됩니다.",
                "",
            "&c&l[판매 불가]",
            ""
        ).modifyMeta<EnchantmentStorageMeta> {
            addStoredEnchant(enchant.id, enchant.id.maxLevel, false)
        }
    }
}