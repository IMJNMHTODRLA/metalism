package _RedGold__.main.core.cartridge.upgradeItem.skill.plasmaCore

enum class PlasmaCoreEnergyEnum(
    val id: String,
    val displayName: String,
    val color: String
) {
    LOW("00", "저에너지", "&1&l"),
    MIDDLE("01", "중에너지", "&9&l"),
    HIGH("02", "고에너지", "&b&l");

    companion object {
        fun getRandom() = entries.random()
    }

    val colorName get() = color + displayName
    val intId get() = id.toIntOrNull()?: (Int.MIN_VALUE / 2)
}

enum class PlasmaCoreTypeEnum(
    val id: String,
    val displayName: String,
    val color: String
) {
    EXPLOSION("00", "폭팔", "&c&l"),
    FIRE("01", "화염", "&6&l"),
    MAGIC("02", "마법", "&d&l");

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
