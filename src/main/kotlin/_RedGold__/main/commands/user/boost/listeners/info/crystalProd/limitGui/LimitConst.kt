package _RedGold__.main.commands.user.boost.listeners.info.crystalProd.limitGui

import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.variableManager.BoostEnum
import _RedGold__.main.managers.playerData.variableManager.BoostSealed
import org.bukkit.entity.Player

object LimitConst {
    val price = listOf(99_000, 99_000, 189_900, 189_900, 369_900)

    val nowRound = { player: Player ->
        player.data.boostMap[enum]?.amount?: 0
    }

    val pack = BoostSealed.LimitCrystalPackage
    val enum = BoostEnum.LimitCrystalPackage
}