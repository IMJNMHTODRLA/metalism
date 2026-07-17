package _RedGold__.main.core.cartridge.upgradeItem.skill.mystery

enum class MysteryStatusEnum(
    val id: String,
    val displayName: String,
    val color: String
) {
    DESTROY("00", "파괴된", "&4&l"),
    LOSE("01", "손실된", "&c&l"),
    RESTORE("02", "복구된", "&e&l"),
    PRESERVE("03","보존된", "&a&l");

    companion object {
        fun getRandom() = entries.random()
    }

    val colorName get() = color + displayName
    val intId get() = id.toIntOrNull()?: (Int.MIN_VALUE / 2)
}

enum class MysteryTypeEnum(
    val id: String,
    val displayName: String,
) {
    VOID("00", "공허"),
    ABYSS("01", "심연"),
    MOMENT("02", "찰나"),
    OBLIVION("03", "망각"),
    DISTORTION("04", "왜곡"),
    WAVE("05", "파동"),
    TORRENT("06", "격류"),
    AFTERIMAGE("07", "잔영"),
    COVENANT("08", "맹약"),
    REVELATION("09", "계시");

    val color = "&7&l"

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
