package _RedGold__.main.managers.playerData.dataManager

import _RedGold__.main.managers.logManager.logObserver
import _RedGold__.main.managers.playerData.logger
import java.util.UUID

data class CombatData(val uuid: UUID) {
    var kill by logObserver(0, logger(uuid))
    var killStreak by logObserver(0, logger(uuid))
    var death by logObserver(0, logger(uuid))
    var deathStreak by logObserver(0, logger(uuid))
}