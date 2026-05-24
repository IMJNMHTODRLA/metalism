package _RedGold__.main.functions

import _RedGold__.main.managers.logManager.LogTypeEnum
import _RedGold__.main.managers.logManager.writeLog

class PluginException (
    override val message: String,
    severity: ExceptionSeverity
) : RuntimeException("[$severity] $message") {
    init { writeLog(message, LogTypeEnum.FAILURE) }
}

enum class ExceptionSeverity {
    NONE,
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL,
    SHUTDOWN
}
