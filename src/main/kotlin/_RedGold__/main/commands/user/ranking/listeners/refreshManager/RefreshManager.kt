package _RedGold__.main.commands.user.ranking.listeners.refreshManager

import _RedGold__.main.commands.user.ranking.listeners.GlobalValue
import _RedGold__.main.functions.Scheduler.taskAsync
import _RedGold__.main.functions.FastNumber.minutes
import _RedGold__.main.functions.FastNumber.seconds
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.loads.RequireJavaPlugin
import org.bukkit.plugin.java.JavaPlugin

@RequireJavaPlugin
class RefreshManager(plugin: JavaPlugin) {
    init {
        RefreshConst.init

        plugin.taskAsync(loop = 2.minutes + 30.seconds) {
            RefreshConst.rankingUpdate

            GlobalValue.nextUpdate = now + 2.minutes + 30.seconds
        }
    }
}