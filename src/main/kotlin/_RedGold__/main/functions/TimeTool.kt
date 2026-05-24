package _RedGold__.main.functions

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter as TimeFormat

object TimeTool {
    val nowMs get() = System.currentTimeMillis()
    val now get() = nowMs / 1000

    data class DaysTimeData(val days: Long, val hours: Long, val minutes: Long, val seconds: Long)

    fun unixToDays(unix: Long): DaysTimeData {
        var timeRemain = unix

        val days = timeRemain / 86400L
        timeRemain %= 86400L
        val hours = timeRemain / 3600L
        timeRemain %= 3600L
        val minutes = timeRemain / 60L
        val seconds = timeRemain % 60L
        return DaysTimeData(days, hours, minutes, seconds)
    }
}

fun Long.formatTimestampMs(format: String = "yyyy년 MM월 dd일"): String =
    Instant.ofEpochSecond(this)
    .atZone(ZoneId.of("Asia/Seoul"))
    .format(TimeFormat.ofPattern(format))

fun Long.formatTimestamp(format: String = "yyyy년 MM월 dd일") = (this * 1000).formatTimestampMs(format)
