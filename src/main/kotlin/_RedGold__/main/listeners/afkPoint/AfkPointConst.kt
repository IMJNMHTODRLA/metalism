package _RedGold__.main.listeners.afkPoint

import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.managers.playerData.PREFIX

internal object AfkPointConst {
    const val AFK_GOLD = 432
    val AFK_MESSAGE = "$PREFIX &a&l10분간 &f&l접속하여 &6&l${AFK_GOLD.toFormat()} 골드&f&l를 획득 하였습니다!"
}