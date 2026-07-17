package _RedGold__.main.functions

import _RedGold__.main.functions.NumberFormat.toUuidOrNull
import _RedGold__.main.functions.TimeTool.now
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

val Any?.isNull get() = this == null

@OptIn(ExperimentalContracts::class)
@JvmName("isSmartNull")
fun Any?.isNull(): Boolean {
    contract { returns(false) implies (this@isNull != null) }
    return this == null
}

val Boolean.reversal get() = !this

val Number.isPositive get() = this.toDouble() >= 0.0
val Number.isZero get() = this.toDouble() == 0.0
val Number.isNegative get() = this.toDouble() < 0.0

fun Long.afterWith(seconds: Number) = (now - this) >= seconds.toLong()
fun Long.remainingWith(seconds: Number) = (this + seconds.toLong() - now).coerceAtLeast(0L)

val String?.isUuidFormat get() = toUuidOrNull() != null

inline fun <reified T> Any?.safeCast() = this as? T
