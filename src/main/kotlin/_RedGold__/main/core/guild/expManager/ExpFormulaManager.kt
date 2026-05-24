package _RedGold__.main.core.guild.expManager

import kotlin.math.pow

fun maxExpOfLevel(level: Int) = (20.0 * level.toDouble().pow(1.4)).toLong()
fun startExpRequire(level: Int) = (1..<level).sumOf { maxExpOfLevel(it) }

fun currentLevel(exp: Long): Int {
    if (exp <= 0) return 1

    var level = 1
    var tempExp = exp
    while (true) {
        val need = maxExpOfLevel(level)
        if (tempExp >= need) {
            tempExp -= need
            level++
        } else break
    }
    return level
}

fun requireExpOfLevel(exp: Long): Long {
    if (exp <= 0) return maxExpOfLevel(1)

    val currentLevel = currentLevel(exp)
    val usedExp = startExpRequire(currentLevel)

    val currentProgressExp = exp - usedExp
    val maxNeed = maxExpOfLevel(currentLevel)

    return maxNeed - currentProgressExp
}

fun nextLevelPercent(max: Long, require: Long) =
    (max - require).toDouble() / max