package _RedGold__.main.functions

@Deprecated("쓰지마", level = DeprecationLevel.WARNING)
inline fun <T> catch(
    message: String,
    severity: ExceptionSeverity,
    block: () -> T
): T {
    return try {
        block()
    } catch (e: Exception) {
        if (e is PluginException) throw e
        throw PluginException("$message ${e.message}", severity)
    }
}