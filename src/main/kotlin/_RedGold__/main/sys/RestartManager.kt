package _RedGold__.main.sys

import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.Color.rgb
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.loads.RequireJavaPlugin
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.plugin.java.JavaPlugin
import java.time.LocalDateTime
import java.time.LocalTime

@RequireJavaPlugin
class RestartManager(private val plugin: JavaPlugin) {
    private val prefix = """
        ${rgb("2444FC")}§l§o[
        ${rgb("304CFC")}§l§oM
        ${rgb("3B54FD")}§l§oE
        ${rgb("475CFD")}§l§oT
        ${rgb("5264FD")}§l§oA
        ${rgb("5E6BFE")}§l§oL
        ${rgb("6973FE")}§l§oI
        ${rgb("757BFE")}§l§oS
        ${rgb("8083FF")}§l§oM
        ${rgb("8C8BFF")}§l§o]
    """.trimIndent().replace("\n", "")

    init {
        val delayTick = run {
            val now = LocalDateTime.now()
            val minute = now.minute
            val second = now.second

            val targetMinute = if (minute < 30) 30 else 60
            val remainingMinutes = targetMinute - minute - 1
            val remainingSeconds = 60 - second

            (remainingMinutes * 60 + remainingSeconds).toLong() * 20L
        }

        plugin.task(delayTick, 20L * 60L * 10L) {
            val now = LocalTime.now()
            val hour = now.hour
            val minute = now.minute

            when {
                hour == 2 && minute == 50 -> {
                    Bukkit.broadcastMessage(gc("$prefix &f&l70분뒤 오전 4시에 서버가 재시작 됩니다.(4시 20분에 재개)"))
                    Bukkit.broadcastMessage(gc("$prefix &c&l3시 30분 이후로 일어나는 손해는 책임 지지 않습니다."))
                    Bukkit.broadcastMessage(gc("$prefix &f&l3시 30분 이후로 이벤트나 PVP를 중단하고 서버에 나가시는걸 &a&l권장드립니다."))
                    for (player in Bukkit.getOnlinePlayers()) player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
                }
                hour == 3 && minute == 10 -> {
                    Bukkit.broadcastMessage(gc("$prefix &f&l50분뒤 오전 4시에 서버가 재시작 됩니다.(4시 20분에 재개)"))
                    Bukkit.broadcastMessage(gc("$prefix &c&l3시 30분 이후로 일어나는 손해는 책임 지지 않습니다."))
                    Bukkit.broadcastMessage(gc("$prefix &f&l3시 30분 이후로 이벤트나 PVP를 중단하고 서버에 나가시는걸 &a&l권장드립니다."))
                    for (player in Bukkit.getOnlinePlayers()) player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
                }
                hour == 3 && minute == 20 -> {
                    Bukkit.broadcastMessage(gc("$prefix &f&l40분뒤 오전 4시에 서버가 재시작 됩니다.(4시 20분에 재개)"))
                    Bukkit.broadcastMessage(gc("$prefix &c&l3시 30분 이후로 일어나는 손해는 책임 지지 않습니다."))
                    Bukkit.broadcastMessage(gc("$prefix &f&l3시 30분 이후로 이벤트나 PVP를 중단하고 서버에 나가시는걸 &a&l권장드립니다."))
                    for (player in Bukkit.getOnlinePlayers()) player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
                }
                hour == 3 && minute == 30 -> {
                    Bukkit.broadcastMessage(gc("$prefix &f&l30분뒤 오전 4시에 서버가 재시작 됩니다.(4시 20분에 재개)"))
                    Bukkit.broadcastMessage(gc("$prefix &c&l3시 30분 이후로 일어나는 손해는 책임 지지 않습니다."))
                    Bukkit.broadcastMessage(gc("$prefix &f&l3시 30분 이후로 이벤트나 PVP를 중단하고 서버에 나가시는걸 &a&l권장드립니다."))
                    for (player in Bukkit.getOnlinePlayers()) player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
                }
                hour == 3 && minute == 40 -> {
                    Bukkit.broadcastMessage(gc("$prefix &f&l20분뒤 오전 4시에 서버가 재시작 됩니다.(4시 20분에 재개)"))
                    Bukkit.broadcastMessage(gc("$prefix &c&l3시 30분 이후로 일어나는 손해는 책임 지지 않습니다."))
                    Bukkit.broadcastMessage(gc("$prefix &f&l지금 이벤트나 PVP를 중단하고 서버에 나가시는걸 &a&l권장드립니다."))
                    for (player in Bukkit.getOnlinePlayers()) player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
                }
                hour == 3 && minute == 50 -> {
                    Bukkit.broadcastMessage(gc("$prefix &f&l10분뒤 오전 4시에 서버가 재시작 됩니다.(4시 20분에 재개)"))
                    Bukkit.broadcastMessage(gc("$prefix &c&l3시 30분 이후로 일어나는 손해는 책임 지지 않습니다."))
                    Bukkit.broadcastMessage(gc("$prefix &f&l지금 당장 이벤트나 PVP를 중단하고 서버에 나가시는걸 &a&l권장드립니다."))
                    for (player in Bukkit.getOnlinePlayers()) player.playSound(player.location, Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f)
                }
                hour == 4 && minute == 0 -> {
                    Bukkit.broadcastMessage(gc("$prefix &a&l서버가 3초 후 서버가 재시작 됩니다...(자동으로 대기열 서버로 이동됩니다.)"))

                    Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop")
                    }, 60L)
                }
            }
        }
    }
}