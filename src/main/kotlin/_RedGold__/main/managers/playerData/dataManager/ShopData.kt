package _RedGold__.main.managers.playerData.dataManager

import _RedGold__.main.managers.playerData.variableManager.DailyEnum

data class ShopData(
    val totalPurchaseDaily: MutableMap<DailyEnum, MutableSet<Int>> = DailyEnum.entries.associateWith {
        mutableSetOf<Int>()
    }.toMutableMap(),

    val totalPurchaseMineral: MutableMap<Int, Int> = mutableMapOf(),
    val totalPurchasePlant: MutableMap<Int, Int> = mutableMapOf(),

    var isPurchaseMonthly: Boolean = false,
)