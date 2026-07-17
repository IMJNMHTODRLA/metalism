package _RedGold__.main.managers.playerData.variableManager.missionManager

import _RedGold__.main.managers.playerData.variableManager.missionManager.missionList.*

enum class MissionEnum(
    val total: Int,

    val infoLink: List<MissionInfoData>,
    val rewardLink: List<MissionRewardData>
) {
    DAILY(TOTAL_DAILY_MISSION, dailyMissionInfoList, dailyMissionRewardList),
    WEEKLY(TOTAL_WEEKLY_MISSION, weeklyMissionInfoList, weeklyMissionRewardList),
    ACHIEVEMENT(TOTAL_ACHIEVEMENT_MISSION, achievementMissionInfoList, achievementMissionRewardList)
    //TODO: GUIDE 미션 만들기
}