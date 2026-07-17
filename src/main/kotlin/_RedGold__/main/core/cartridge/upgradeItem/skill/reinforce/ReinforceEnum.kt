package _RedGold__.main.core.cartridge.upgradeItem.skill.reinforce

enum class ReinforceImportEnum(
    val id: String,
    val displayName: String,
    val color: String
) {
    GENERAL("00", "일반", "&7&l"),
    BASIC("01", "하급", "&f&l"),
    BEGINNER("02", "초급", "&e&l"),
    INTERMEDIA("03","중급", "&6&l"),
    ADVANCED("04", "고급", "&d&l");

    companion object {
        fun getRandom() = entries.random()
    }

    val colorName get() = color + displayName
    val intId get() = id.toIntOrNull()?: (Int.MIN_VALUE / 2)
}

enum class ReinforceTypeEnum(
    val id: String,
    val displayName: String,
    val color: String
) {
    HEAL("00", "회복", "&a&l"),
    COMBAT("01", "전투", "&c&l"),
    MOVEMENT("02", "이속", "&b&l"),
    UNDERWATER("03", "수중", "&9&l"),
    SUPPORT("04", "서폿", "&9&l");

    companion object {
        fun getRandom() = entries.random()
    }

    val colorName get() = color + displayName
    val intId: Int
        get() {
            val rawValue = id.toIntOrNull()?: (Int.MIN_VALUE / 2)
            return rawValue * 100
        }
}
