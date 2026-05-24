package _RedGold__.main.listeners.autoNotice

import _RedGold__.main.functions.Color.broadcast
import _RedGold__.main.functions.FastNumber.minutes
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.loads.RequireJavaPlugin
import org.bukkit.plugin.java.JavaPlugin

@RequireJavaPlugin
class AutoNoticeListener(plugin: JavaPlugin) {
    init {
        plugin.task(0, 10.minutes) {
            AutoNoticeConst.NOTICE_MESSAGE[
                AutoNoticeValue.times % AutoNoticeConst.NOTICE_MESSAGE.size
            ].broadcast()

            AutoNoticeValue.times++
        }
    }
}