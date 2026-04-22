package _RedGold__.main.managers.rebootManager

import _RedGold__.main.functions.Scheduler.task
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class RebootManager(private val plugin: JavaPlugin) {
    private val scheduler = Executors.newSingleThreadScheduledExecutor()

    init {
        val now = LocalDateTime.now()
        var target = now.withHour(4).withMinute(0).withSecond(0).withNano(0)
        if (now >= target) target = target.plusDays(1)

        // 남은 시간 계산
        val delay = Duration.between(now, target).toSeconds()
        plugin.logger.info("서버가 $delay 초 후에 종료됩니다. (새벽 4시)")

        // 대기 후 종료 명령 실행
        scheduler.schedule({
            plugin.task {
                Bukkit.shutdown()
            }
        }, delay, TimeUnit.SECONDS)
    }

    fun stop() {
        scheduler.shutdownNow()

        try {
            if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                plugin.logger.warning("RebootManager 스케줄러가 완전히 종료되지 않았습니다.")
            }
        } catch (e: InterruptedException) {
            scheduler.shutdownNow()
        }
    }
}