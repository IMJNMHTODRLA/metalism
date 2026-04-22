package _RedGold__.main.functions

class PluginException (
    override val message: String,
    severity: ExceptionSeverity
) : RuntimeException("[$severity] $message")

enum class ExceptionSeverity {
    NONE,
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL,
    SHUTDOWN
}
