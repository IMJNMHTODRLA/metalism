package _RedGold__.main.commands.user.ranking.listeners.refreshManager

import _RedGold__.main.commands.user.ranking.listeners.GlobalValue
import _RedGold__.main.functions.FastNumber.minutes
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.functions.task
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.loads.SetSlowInit
import org.bukkit.Bukkit

@SetSlowInit
object RefreshManager {
    @SetSlowInit
    fun startRefreshManager() {
        val loopTime = 5.minutes

        RefreshConst.onlinePlayers = Bukkit.getOnlinePlayers()
        RefreshConst.init()

        task(loop = loopTime) {
            RefreshConst.onlinePlayers = Bukkit.getOnlinePlayers()
        }

        taskAsync(loop = loopTime) {
            RefreshConst.rankingUpdate()

            GlobalValue.nextUpdate = now + loopTime
        }
    }
}