package _RedGold__.main.managers.logManager

import _RedGold__.main.Main
import _RedGold__.main.loads.SetFinalFlush
import _RedGold__.main.loads.SetSlowInit
import java.io.File
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

@SetSlowInit
fun initLog() {
    if (isInit) return

    val current = LocalDateTime.now()

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val formatted = current.format(formatter)

    logFileName = "logma-$formatted"
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
    val plugin = Main.instance
    val logFile = File(plugin.dataFolder, "logma/$logFileName.log")
    logFile.writeText(logData.joinToString("\n"))
}
