package _RedGold__.main.managers.playerData.variableManager

const val TOTAL_DAILY_MISSION = 6
const val TOTAL_WEEKLY_MISSION = 6
const val TOTAL_ACHIEVEMENT_MISSION = 15

enum class MissionEnum(
    val total: Int
) {
    DAILY(TOTAL_DAILY_MISSION),
    WEEKLY(TOTAL_WEEKLY_MISSION),
    ACHIEVEMENT(TOTAL_ACHIEVEMENT_MISSION)
}
