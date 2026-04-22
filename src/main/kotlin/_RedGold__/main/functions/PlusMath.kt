package _RedGold__.main.functions

object PlusMath {
    fun Int.pow(n: Int): Int {
        var result = 1
        repeat(n) { result *= this }
        return result
    }

    fun Long.pow(n: Int): Long {
        var result = 1L
        repeat(n) { result *= this }
        return result
    }
}