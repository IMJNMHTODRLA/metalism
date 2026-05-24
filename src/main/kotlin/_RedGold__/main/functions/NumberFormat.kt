package _RedGold__.main.functions

import java.text.NumberFormat
import java.util.*

object NumberFormat {
    fun Long.toFormat(): String = NumberFormat.getInstance().format(this)
    fun Int.toFormat(): String = NumberFormat.getInstance().format(this)

    fun String?.toUuid(): UUID {
        return try {
            if (this == null) return UUID.randomUUID()
            if (contains("-")) UUID.fromString(this)
            else UUID.fromString(replaceFirst(
                "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})".toRegex(),
                "$1-$2-$3-$4-$5"
            ))
        } catch (e: IllegalArgumentException) {
            UUID.randomUUID()
        }
    }

    fun String?.toUUIDOrNull(): UUID? {
        return try {
            if (this == null) return null
            if (contains("-")) UUID.fromString(this)
            else UUID.fromString(replaceFirst(
                "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})".toRegex(),
                "$1-$2-$3-$4-$5"
            ))
        } catch (e: IllegalArgumentException) {
            null
        }
    }

    fun Float.toFormat(number: Int): String {
        val formatter = NumberFormat.getInstance()

        formatter.minimumFractionDigits = number
        formatter.maximumFractionDigits = number

        return formatter.format(this)
    }

    fun Double.toFormat(number: Int): String {
        val formatter = NumberFormat.getInstance()

        formatter.minimumFractionDigits = number
        formatter.maximumFractionDigits = number

        return formatter.format(this)
    }

    fun Long.toTimeFormat(): String {
        val minutes = this / 60
        val seconds = this % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}