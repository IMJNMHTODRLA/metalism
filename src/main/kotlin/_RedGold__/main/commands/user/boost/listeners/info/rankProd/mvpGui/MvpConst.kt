package _RedGold__.main.commands.user.boost.listeners.info.rankProd.mvpGui

import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.core.gacha.item.skill.reinforceManager.ReinforceImportEnum
import _RedGold__.main.core.gacha.item.skill.reinforceManager.randomReinforceItem

object MvpConst {
    const val PRICE = 28_900

    val giveRank = PermissionEnum.MVP

    const val BONUS_GOLD = 600_000
    const val BONUS_CRYSTAL = 360
    val bonusItem = randomReinforceItem(1, ReinforceImportEnum.INTERMEDIA)
}