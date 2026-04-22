package _RedGold__.main.managers.reinforceManager

import org.bukkit.Material

const val MAX_REINFORCE_TYPE = 4

val ReinforceItemList = listOf(
    Material.OAK_SAPLING, //뿌리기 가능, 8천 골드, 일반
    Material.OXEYE_DAISY, //too, 1.2만 골드, 하급
    Material.SWEET_BERRIES, //too, 2.4만 골드, 초급
    Material.APPLE, //얘는 엔컨에서, 중급
    Material.ENCHANTED_GOLDEN_APPLE, //얘는 뿌리기 OR 지급 X, 만렙 직전 레벨(OR 8레벨 부터 1개 필요)에서 **1개** 사용, 고급
    //회복 타입

    Material.STICK,
    Material.IRON_INGOT,
    Material.END_CRYSTAL,
    Material.DRAGON_BREATH,
    Material.TRIDENT,
    //전투 타입

    Material.FIREWORK_ROCKET,
    Material.RABBIT_FOOT,
    Material.COMPASS,
    Material.SADDLE,
    Material.BEACON,
    //이속 타임

    Material.GLASS_BOTTLE,
    Material.WATER_BUCKET,
    Material.FISHING_ROD,
    Material.PUFFERFISH,
    Material.TURTLE_HELMET
    //수중 타입
)