package _RedGold__.main.core.guild.expManager.expUpdate

import _RedGold__.main.core.guild.expManager.addGuildExp
import _RedGold__.main.core.guild.expManager.currentLevel
import _RedGold__.main.core.guild.expManager.guildDelayAddExp
import _RedGold__.main.core.guild.expManager.guildLevelCache
import _RedGold__.main.core.guild.shareChestManager.shareChestUpdate
import _RedGold__.main.core.guild.updateTick
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.loads.SetFinalFlush
import _RedGold__.main.loads.SetSlowInit

@SetSlowInit
object ExpUpdate {
    @Volatile private var shareGuildStats = allGetGuildStats()

    @SetSlowInit
    fun startUpdateTask() {
        taskAsync(loop = updateTick) {
            shareGuildStats = allGetGuildStats()

            refresh()
            addExpReward()
            shareChestUpdate()
        }
    }

    private fun refresh() {
        guildLevelCache.clear()

        shareGuildStats.forEach {
            guildLevelCache[it.id] = currentLevel(it.exp)
        }
    }

    /* TODO: 이게 될까? */
    @SetFinalFlush
    private fun addExpReward() {
        guildDelayAddExp.forEach { (id, amount) ->
            addGuildExp(id, amount)
        }
    }
}