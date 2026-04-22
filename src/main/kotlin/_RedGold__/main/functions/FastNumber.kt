package _RedGold__.main.functions

object FastNumber {
    inline val Int.ticks: Long get() = this.toLong()
    inline val Int.seconds: Long get() = this * 20L
    inline val Int.minutes: Long get() = this * 1200L // 20 * 60
    inline val Int.hours: Long get() = this * 72000L
}