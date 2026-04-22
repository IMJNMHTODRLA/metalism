package _RedGold__.main.functions

object Cubic {
    infix fun <T> Boolean.then(value: T): T? = if (this) value else null
    infix fun <T> T?.orElse(defaultValue: T): T = this?: defaultValue
}