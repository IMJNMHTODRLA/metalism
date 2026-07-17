package _RedGold__.main.event._showDown_.managers

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.function.api.toFormat
import org.bukkit.plugin.java.JavaPlugin

class MinionManager(
    val plugin: JavaPlugin,
    private val session: BossSession,
) {
    fun spawnMinion() {
        plugin.task(2400, 2400, fun(min2Task) {
            if (!session.isRunning) {
                min2Task.cancel()
                return
            }

            var healthStack = 0.0
            session.minions.removeIf {minion ->
                if (minion.isDead) return@removeIf true

                healthStack += minion.health
                minion.remove()
                true
            }
            if (healthStack > 0) session.player.sendMsg("&c&l보스의 체력이 ${healthStack.toFormat(0)} 회복되었습니다.")

            for (i in 0..5) session.minions.add(
                session.bossEntity.minionSpawn(session.player, session.boss, session.minionMetadata)
            )
        })
    }
}