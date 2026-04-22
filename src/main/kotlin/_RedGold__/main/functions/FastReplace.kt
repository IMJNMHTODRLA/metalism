package _RedGold__.main.functions

object FastReplace {
    fun String.fill(vararg pairs: Pair<String, Any>): String {
        var result = this
        pairs.forEach { (key, value) ->
            result = result.replace("%$key%", value.toString())
        }
        return result
    }

    fun String.gap(vararg pairs: String): String {
        var result = this
        pairs.forEach { part ->
            result = result.replace(part, "")
        }
        return result
    }
}