package _RedGold__.main.functions

object FastList {
    inline operator fun <T> List<T>.get(index: Int, nullWork: (Int) -> T): T {
        return this.getOrElse(index) { nullWork(it) }
    }
}