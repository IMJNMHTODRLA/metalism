package _RedGold__.main.managers.playerData.variableManager.missionManager

enum class MissionEnum(
    val total: Int
) {
    DAILY(TOTAL_DAILY_MISSION),
    WEEKLY(TOTAL_WEEKLY_MISSION),
    ACHIEVEMENT(TOTAL_ACHIEVEMENT_MISSION)
    //TODO: GUIDE 미션 만들기
}