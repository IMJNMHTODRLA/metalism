package _RedGold__.main.command.shop.sys.goldShop.plantGui

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
class PlantReset(private val plugin: JavaPlugin) {
    init {
        loop()
    }

    private fun loop() {
        val path = plugin.dataFolder.toPath().resolve("plant_shop")

        val nowKST = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        var nextWeek = nowKST.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(0).withMinute(0).withSecond(0).withNano(0)

        // 이미 지났으면 내일 자정
        if (!nextWeek.isAfter(nowKST)) nextWeek = nextWeek.plusWeeks(1)

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, Runnable {
            Files.walk(path).use { stream ->
                stream.filter { Files.isRegularFile(it) && it.toString().endsWith(".data") }
                    .forEach { file ->
                        Files.writeString(file, "0", StandardOpenOption.TRUNCATE_EXISTING)
                    }
            }

            loop()
        }, Duration.between(nowKST, nextWeek).seconds * 20L)
    }
}