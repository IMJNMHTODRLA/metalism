package _RedGold__.main.commands.mvp.nick

import _RedGold__.main.functions.FastReplace.gap

internal object NickConst {
    private val REGEX = Regex("^[a-zA-Z0-9가-힣_ ~`!@#$%^&*()\\\\\\-]+$")

    val IS_PERFECT_NAME = { name: String ->
        val cleanName = name.gap("§")
        (cleanName.length in 3..20) && REGEX.matches(cleanName)
    }
}