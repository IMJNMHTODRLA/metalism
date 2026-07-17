package _RedGold__.main.functions

import java.time.format.DateTimeFormatter
import java.time.temporal.Temporal

fun Temporal.toFormat(pattern: String): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return formatter.format(this)
}
