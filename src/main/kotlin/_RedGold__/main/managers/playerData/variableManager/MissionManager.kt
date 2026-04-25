package _RedGold__.main.managers.playerData.variableManager

const val TOTAL_DAILY_MISSION = 7
const val TOTAL_WEEKLY_MISSION = 6
const val TOTAL_ACHIEVEMENT_MISSION = 15

data class MissionInfo(
    val title: String,
    val max: Int,
    val reward: String
)

val dailyMissionInfoList = listOf(
    MissionInfo("일일 접속", 1, "&6&l5,000 골드 지급"),
    MissionInfo("일일 상점에서 아이템 구매", 1, "&6&l10,000 골드 지급"),
    MissionInfo("블록 설치 20회", 20, "&6&l5,000 골드 지급"),
    MissionInfo("블록 파괴 20회", 20, "&6&l5,000 골드 지급"),
    MissionInfo("엔티티 처치 10회", 10, "&6&l5,000 골드 지급"),
    MissionInfo("플레이어 처치", 1, "&6&l5,000 골드 지급"),
    MissionInfo("일일 미션 5회 완료", 5, "&b&l20 크리스탈 지급")
)
val weeklyMissionListName = listOf(
    "일일 접속", "일일 상점에서 아이템 구매",
    "블록 설치 20회", "블록 파괴 20회",
    "엔티티 처치 10회", "플레이어 처치",
    "일일 미션 5회 완료"
)

enum class MissionEnum(
    val total: Int
) {
    DAILY(TOTAL_DAILY_MISSION),
    WEEKLY(TOTAL_WEEKLY_MISSION),
    ACHIEVEMENT(TOTAL_ACHIEVEMENT_MISSION)
}
