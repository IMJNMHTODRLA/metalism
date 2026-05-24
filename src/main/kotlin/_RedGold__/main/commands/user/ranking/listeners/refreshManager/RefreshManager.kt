package _RedGold__.main.commands.user.ranking.listeners.refreshManager

import _RedGold__.main.commands.user.ranking.listeners.GlobalValue
import _RedGold__.main.functions.FastNumber.minutes
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.loads.SetSlowInit

@SetSlowInit
class RefreshManager {
    init {
        val loopTime = 5.minutes
        RefreshConst.init

        taskAsync(loop = loopTime) {
            RefreshConst.rankingUpdate

            GlobalValue.nextUpdate = now + loopTime
        }
    }
}