package _RedGold__.main.listeners.afkPoint

import _RedGold__.main.functions.Color.broadcast
import _RedGold__.main.functions.FastNumber.minutes
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.managers.playerData.data
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

@RequireJavaPlugin
class AfkPointListener(plugin: JavaPlugin) {
    init {
        plugin.task(0, 10.minutes) {
            Bukkit.getOnlinePlayers().forEach { player ->
                player.data.gold += AfkPointConst.AFK_GOLD
            }

            AfkPointConst.AFK_MESSAGE.broadcast()
        }
    }
}