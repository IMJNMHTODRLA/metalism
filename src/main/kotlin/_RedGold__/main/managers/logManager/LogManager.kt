package _RedGold__.main.managers.logManager

import _RedGold__.main.Main
import _RedGold__.main.functions.toFormat
import _RedGold__.main.loads.SetFinalFlush
import _RedGold__.main.loads.SetSlowInit
import java.io.File
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

@SetSlowInit
fun initLog() {
    if (isInit) return

    val current = LocalDateTime.now()

    val datePart = current.toLocalDate()
    val timePart = current.toLocalTime()

    val dateString = datePart.toFormat("yyyy-MM-dd")
    val timeString = timePart.toFormat("HH시 mm분 ss초")

    data = dateString
    time = timeString

    isInit = true
}

fun writeLog(logContent: String, enum: LogTypeEnum = LogTypeEnum.INFO) {
    logData.add("[${LocalTime.now().withNano(0)}] [${enum.name}] $logContent")
}

fun <T> logObserver(
    initValue: T,
    msg: (
        prop: KProperty<*>, old: T, new: T
    ) -> String
) = Delegates.observable(initValue) { prop, old, new ->
    if (old != new) writeLog(msg(prop, old, new))
}

@SetFinalFlush
fun saveLog() {
    if (!isInit) initLog()

    val plugin = Main.instance

    val logDir = File(plugin.dataFolder, "logma/${data}")
    if (!logDir.exists()) logDir.mkdirs()

    val logFile = File(logDir, "$time.log")
    logFile.writeText(logData.joinToString("\n"))
}
