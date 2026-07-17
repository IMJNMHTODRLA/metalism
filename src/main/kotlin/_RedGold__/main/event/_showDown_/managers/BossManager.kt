package _RedGold__.main.event._showDown_.managers

import _RedGold__.main.event._showDown_.DataManager.story
import _RedGold__.main.functions.Color.sendMsg
import com.github.shynixn.mccoroutine.bukkit.launch
import com.github.shynixn.mccoroutine.bukkit.ticks
import kotlinx.coroutines.delay
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class BossManager {
    fun battleStart(
        plugin: JavaPlugin,
        player: Player,
        difficulty: Int
    ) {
        plugin.launch {
            delay(40.ticks)
            for (text in story) {
                player.sendMsg(text)
                delay(40.ticks)
            }

            val bossSession = BossSession(plugin, player, difficulty)
            bossSession.init()
        }
    }
}