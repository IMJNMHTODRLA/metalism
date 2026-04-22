package _RedGold__.main.functions

object TimeTool {
    val now get() = System.currentTimeMillis() / 1000
    val nowMs get() = System.currentTimeMillis()

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