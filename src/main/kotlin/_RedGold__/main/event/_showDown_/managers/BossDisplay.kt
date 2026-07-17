package _RedGold__.main.event._showDown_.managers

import _RedGold__.main.event._showDown_.DataManager.BOSS_LORE
import _RedGold__.main.event._showDown_.DataManager.BOSS_NAME
import _RedGold__.main.event._showDown_.DataManager.BOSS_NAME_ASCII
import _RedGold__.main.event._showDown_.EventMetaDatas
import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.Cubic.orElse
import _RedGold__.main.functions.Cubic.then
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.function.api.toTimeFormat
import org.bukkit.Sound
import org.bukkit.boss.BarColor
import org.bukkit.plugin.java.JavaPlugin

class BossDisplay(
    val plugin: JavaPlugin,
    private val session: BossSession,
) {
    fun loopTime() {
        var setTime = session.player.playerTime.coerceIn(0L, 24000L)

        plugin.task(0, 2, fun(task) {
            if (!session.isRunning) {
                task.cancel()
                return
            }

            session.player.setPlayerTime(setTime % 24000, false)
            setTime += 100
        })
    }

    fun showTitle() {
        session.player.sendTitle(gc(
            "&e&l$BOSS_NAME [The ${session.difficulty + 1}th Archivist]"), gc("&7&lThe History of &f&l$BOSS_LORE"
        ), 0, 30, 0)
        session.player.sendSound(Sound.ENTITY_ENDER_DRAGON_AMBIENT)
    }

    fun bossBarTask() {
        plugin.task(0, 5, fun(task) {
            if (!session.isRunning) {
                session.bossBar.removeAll()
                task.cancel()
                return
            }
            bossBarUpdate()
        })
    }

    private fun bossBarUpdate() {
        val hp = session.boss.health
        val unit = 500.0
        val unitHp = (hp % unit == 0.0 && hp > 0) then unit orElse hp % unit

        session.bossBar.color = (hp < unit) then BarColor.WHITE orElse BarColor.BLUE
        session.bossBar.setTitle(gc("&e&l$BOSS_NAME_ASCII &8| &b&l${hp.toFormat(0)}&8/&b&l${unit.toFormat(0)} &2&lHP"))
        session.bossBar.progress = (unitHp / unit).coerceIn(0.0, 1.0)

        val now = System.currentTimeMillis() / 1000
        val taken = EventMetaDatas.BATTLE_TIME - (now - session.time) //남은 시간
        val timeStr = taken.coerceAtLeast(0L).toTimeFormat()

        val timeColor = if (taken < 300) "&c&l" else "&e&l"
        session.player.sendActionBar("${timeColor}${timeStr}")
    }
}