package _RedGold__.main.command.shop.sys.dailyShop

import _RedGold__.main.load.RequireJavaPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime

@RequireJavaPlugin
class DailyReset(private val plugin: JavaPlugin) {
    init {
        loop()
    }

    private fun loop() {
        val path = plugin.dataFolder.toPath().resolve("daily_shop")

        val nowKST = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        var nextDay = nowKST.withHour(0).withMinute(0).withSecond(0).withNano(0)

        // 이미 지났으면 내일 자정
        if (!nextDay.isAfter(nowKST)) nextDay = nextDay.plusDays(1)

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, Runnable {
            Files.walk(path).use { stream ->
                stream.filter { Files.isRegularFile(it) && it.toString().endsWith(".data") }
                    .forEach { file ->
                        Files.writeString(file, "0", StandardOpenOption.TRUNCATE_EXISTING)
                    }
            }
            loop()
        }, Duration.between(nowKST, nextDay).seconds * 20L)
    }
}