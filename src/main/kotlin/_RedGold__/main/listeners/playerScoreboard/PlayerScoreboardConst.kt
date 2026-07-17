package _RedGold__.main.listeners.playerScoreboard

import _RedGold__.main.functions.Color.gc
import _RedGold__.main.managers.playerData.SERVER_ADDRESS

object PlayerScoreboardConst {
    const val PLAYER_TAB_PREFIX = "%style%%rank% %name%"

    val SIDEBAR_MSG = listOf(
        "&f&l플레이어: %fullPrefix%".gc(),
        "",
        "&f&l골드: &6&l%gold% 골드".gc(),
        "&f&l크리스탈: &b&l%crystal% 크리스탈".gc(),
        "&f&l루비: &4&l%ruby% 루비".gc(),
        " ",
        "&f&l누적 킬: &a&l%kill%".gc(),
        "&f&l누적 데스: &c&l%death%".gc(),
        "  ",
        "&f&l추천: &c&lX".gc(),
        "   ",
        "&8$SERVER_ADDRESS | %ping% ms".gc()
    )
}