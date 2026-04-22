package _RedGold__.main.commands.mission.sys

import _RedGold__.main.commands.mission.sys.dailyGui.DailyUpdate.DailyUpdate.isGet
import _RedGold__.main.loads.RequireJavaPlugin
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
        val path = plugin.dataFolder.toPath().resolve("mission/daily/progress")
        val path1 = plugin.dataFolder.toPath().resolve("mission/daily/get")

        val nowKST = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        var nextDay = nowKST.withHour(0).withMinute(0).withSecond(0).withNano(0)

        if (!nextDay.isAfter(nowKST)) nextDay = nextDay.plusDays(1)

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
        }, Duration.between(nowKST, nextDay).seconds * 20L)
    }
}