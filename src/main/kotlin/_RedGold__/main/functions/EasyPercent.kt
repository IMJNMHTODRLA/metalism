package _RedGold__.main.functions

import _RedGold__.main.functions.NumberFormat.toFormat

val Double.toPercent get() = "${(this * 100).toFormat(1)}%"