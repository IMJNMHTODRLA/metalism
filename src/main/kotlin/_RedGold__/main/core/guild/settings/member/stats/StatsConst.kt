package _RedGold__.main.core.guild.settings.member.stats

import kotlin.math.roundToInt

fun makeProgressBar(ratio: Double, totalBlocks: Int = 10): String {
    val filledCount = (ratio * totalBlocks).roundToInt()
    val emptyCount = totalBlocks - filledCount

    // 문자열 반복 생성 후 합치기
    return "[${"■".repeat(filledCount)}${"□".repeat(emptyCount)}]"
}