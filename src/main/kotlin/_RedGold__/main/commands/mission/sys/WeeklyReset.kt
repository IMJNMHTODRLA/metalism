package _RedGold__.main.commands.mission.sys

import _RedGold__.main.commands.mission.sys.weeklyGui.WeeklyUpdate.WeeklyUpdate.isGet
import _RedGold__.main.loads.RequireJavaPlugin
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
class WeeklyReset(private val plugin: JavaPlugin) {
    init {
        loop()
    }

    private fun loop() {
        val path = plugin.dataFolder.toPath().resolve("mission/weekly/progress")
        val path1 = plugin.dataFolder.toPath().resolve("mission/weekly/get")

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

            Files.walk(path1).use { stream ->
                stream.filter { Files.isRegularFile(it) && it.toString().endsWith(".data") }
                    .forEach { file ->
                        Files.writeString(file, "0", StandardOpenOption.TRUNCATE_EXISTING)
                    }
            }

            isGet.clear()
            loop()
        }, Duration.between(nowKST, nextWeekly).seconds * 20L)
    }
}