package _RedGold__.main.managers.playerData.variableManager

enum class BoostSettingEnum(val default: Int) {
    CHAT_GG_COLOR(0),
    USAGE_CHAT_INV(0)
}

const val TOTAL_CHAT_GG_COLOR = 17
val chatGGColorList: List<String> = listOf(
    "&f", "&0&l", "&1&l", "&2&l", "&3&l", "&4&l", "&5&l", "&6&l", "&7&l", "&8&l", "&9&l",
    "&a&l", "&b&l", "&c&l", "&d&l", "&e&l", "&f&l"
)
