package _RedGold__.main.managers.reinforceManager

enum class ReinforceImportEnum(
    val displayName: String,
    val color: String
) {
    GENERAL("일반", "&7&l"),
    BASIC("하급", "&f&l"),
    BEGINNER("초급", "&e&l"),
    INTERMEDIA("중급", "&6&l"),
    ADVANCED("고급", "&d&l")
}

enum class ReinforceTypeEnum(
    val displayName: String,
    val color: String
) {
    HEAL("회복", "&a&l"),
    COMBAT("전투", "&c&l"),
    MOVEMENT("이속", "&b&l"),
    UNDERWATER("수중", "&5&l"),
}
