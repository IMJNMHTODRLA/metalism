package _RedGold__.main.command.shop.sys.cashShop.ticketGui

import _RedGold__.main.load.RequireJavaPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.time.DayOfWeek
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.TemporalAdjusters

@RequireJavaPlugin
class TicketReset(private val plugin: JavaPlugin) {
    init {
        loop()
    }

    private fun loop() {
        val path = plugin.dataFolder.toPath().resolve("ticket").resolve("buy")

        val nowKST = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        var nextWeekly = nowKST.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
            .withHour(0)
            .withMinute(0)
            .withSecond(0)
            .withNano(0)

        if (!nextWeekly.isAfter(nowKST)) nextWeekly = nextWeekly.plusWeeks(1)

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, Runnable {
            Files.walk(path).use { stream ->
                stream.filter { Files.isRegularFile(it) && it.toString().endsWith(".data") }
                    .forEach { file ->
                        Files.writeString(file, "0", StandardOpenOption.TRUNCATE_EXISTING)
                    }
            }

            loop()
        }, Duration.between(nowKST, nextWeekly).seconds * 20)
    }
}