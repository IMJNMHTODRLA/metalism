package _RedGold__.main.commands.user.betting.listeners.lottoGui

import _RedGold__.main.functions.Color.rgb
import org.bukkit.Sound

internal object LottoConst {
    const val AMOUNT = 5000

    val WIN_MESSAGE = listOf(
        "&8&l추첨에서 낙첨되었습니다...(0자리 일치)", // 7등
        "&7&l추첨에서 낙첨되었습니다...(1자리 일치)", // 6등
        "&7&l추첨에서 2자리를 맞춰 5등이 되었습니다.", // 5등
        "&f&l축하합니다! 추첨에서 3자리를 맞춰 4등이 되었습니다!", // 4등
        "&e&l축하합니다!! 추첨에서 4자리를 맞춰 3등이 되었습니다!!", // 3등
        "&6&l축하합니다!!! 추첨에서 무려 5자리를 맞춰 2등을 차지하였습니다!!!", // 2등
        "&b&l축하합니다!!!! 추첨에서 무려 6자리 모두 맞춰 1등에 당첨되었습니다!!!!" // 1등
    )

    val WIN_SOUND = listOf(
        Sound.ENTITY_LIGHTNING_BOLT_IMPACT, // 7등
        Sound.ENTITY_LIGHTNING_BOLT_IMPACT, // 6등
        Sound.ENTITY_PLAYER_ATTACK_CRIT, // 5등
        Sound.ENTITY_EXPERIENCE_ORB_PICKUP, // 4등
        Sound.ENTITY_PLAYER_LEVELUP, // 3등
        Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, // 2등
        Sound.ENTITY_FIREWORK_ROCKET_TWINKLE // 1등
    )

    val WIN_PRIZE = listOf(
        0, //7등
        0, //6등
        2500, //5등
        5000, //4등
        100_000, //3등
        2_500_000, //2등
        125_000_000L //1등
    )
}