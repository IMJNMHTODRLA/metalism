package _RedGold__.main.function

object Color {
    fun rgb(rgb: String): String {
        val result = StringBuilder("§x")
        for (c in rgb.toCharArray()) {
            result.append("§").append(c)
        }

        return result.toString()
    }

    fun gc(msg: String): String {
        return msg.replace("&", "§")
    }
}