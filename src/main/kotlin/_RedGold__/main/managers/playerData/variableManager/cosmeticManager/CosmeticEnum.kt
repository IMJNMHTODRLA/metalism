package _RedGold__.main.managers.playerData.variableManager.cosmeticManager

enum class CosmeticEnum(
    val total: Int,
    val link: List<*>,
    val typeName: String,
    val where: List<String>
) {
    STYLE(TOTAL_STYLE_COSMETIC, STYLE_COSMETIC, "칭호", STYLE_WHERE),
    JOIN(TOTAL_JOIN_COSMETIC, JOIN_COSMETIC, "접속 메시지", JOIN_WHERE),
    DEATH(TOTAL_DEATH_COSMETIC, DEATH_COSMETIC, "사망 사운드", DEATH_WHERE),
    KILL(TOTAL_KILL_COSMETIC, KILL_COSMETIC, "킬 사운드", KILL_WHERE);
}
