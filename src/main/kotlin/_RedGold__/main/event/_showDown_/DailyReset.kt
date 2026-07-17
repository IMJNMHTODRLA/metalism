package _RedGold__.main.event._showDown_

import _RedGold__.main.event._showDown_.DataManager.END_TIME
import _RedGold__.main.event._showDown_.DataManager.START_TIME
import _RedGold__.main.functions.Scheduler.taskAsync
import _RedGold__.main.loads.RequireJavaPlugin
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@RequireJavaPlugin
class DailyReset(private val plugin: JavaPlugin) {
    init {
        loop()
    }

    private fun loop() { //나중에 만들어야 함
        val path = plugin.dataFolder.toPath().resolve("randomEffect").resolve("max")

        val nowKST = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        var nextDay = nowKST.withHour(11).withMinute(0).withSecond(0).withNano(0)
        if (!nextDay.isAfter(nowKST)) nextDay = nextDay.plusDays(1)

        plugin.taskAsync(Duration.between(nowKST, nextDay).seconds * 20L) {
            val now = LocalDateTime.now()
            if (now.isBefore(START_TIME) || !now.isBefore(END_TIME)) {
                nextEvent = -1L
                return@taskAsync
            }

            max.replaceAll {_, _ -> 0}

            Files.walk(path).use { stream ->
                stream.filter { Files.isRegularFile(it) && it.toString().endsWith(".data") }
                    .forEach { file ->
                        Files.writeString(file, "0", StandardOpenOption.TRUNCATE_EXISTING)
                    }
            }

            loop()
        }
    }
}