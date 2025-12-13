package _RedGold__.main.command.shop.sys.monthlyShop

import _RedGold__.main.load.RequireJavaPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime

@RequireJavaPlugin
class MonthlyReset(private val plugin: JavaPlugin) {
    init {
        loop()
    }

    private fun loop() {
        val path = plugin.dataFolder.toPath().resolve("monthly_shop")

        val nowKST = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        var nextMonthly = nowKST.withDayOfMonth(1)
            .withHour(0)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)

        // 이미 지났으면 내일 자정
        if (!nextMonthly.isAfter(nowKST)) nextMonthly = nextMonthly.plusMonths(1)

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, Runnable {
            Files.walk(path).use { stream ->
                stream.filter { Files.isRegularFile(it) && it.toString().endsWith(".data") }
                    .forEach { file ->
                        Files.writeString(file, "0", StandardOpenOption.TRUNCATE_EXISTING)
                    }
            }

            loop()
        }, Duration.between(nowKST, nextMonthly).seconds * 20L)
    }
}