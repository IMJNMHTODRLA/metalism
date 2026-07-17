package _RedGold__.main.listeners.playerJoinQuit

import _RedGold__.main.managers.playerData.variableManager.BoostSealed

object PlayerJoinQuitConst {
    val monthly = BoostSealed.MonthlyPackage

    val dailyGiveMessage = """
        &a&l월간 패키지의 혜택으로 %crystal% 크리스탈을 받았습니다.
        &8&l남은 기간: %day_exp%일
    """.trimIndent()
}