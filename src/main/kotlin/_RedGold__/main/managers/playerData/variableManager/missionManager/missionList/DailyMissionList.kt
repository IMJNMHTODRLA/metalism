package _RedGold__.main.managers.playerData.variableManager.missionManager.missionList

import _RedGold__.main.managers.playerData.variableManager.missionManager.MissionInfoData
import _RedGold__.main.managers.playerData.variableManager.missionManager.MissionRewardData
import _RedGold__.main.core.gacha.item.skill.reinforceManager.ReinforceImportEnum
import _RedGold__.main.core.gacha.item.skill.reinforceManager.randomReinforceItem

val dailyMissionInfoList = listOf(
    MissionInfoData("일일 접속", 1, "&6&l5,000 골드 지급"), //완
    MissionInfoData("일일 상점에서 아이템 구매", 1, "&e&l무작위 일반 강화석 지급"), //완
    MissionInfoData("블록 설치 20회", 20, "&e&l무작위 일반 강화석 지급"), //완
    MissionInfoData("블록 파괴 20회", 20, "&e&l무작위 일반 강화석 지급"), //완
    MissionInfoData("엔티티 처치 10회", 10, "&6&l15,000 골드 지급"), //완
    MissionInfoData("플레이어 처치", 1, "&e&l무작위 하급 강화석 지급"), //완
    MissionInfoData("일일 미션 5회 완료", 5, "&b&l20 크리스탈 지급") //완
)

val dailyMissionRewardList = listOf(
    MissionRewardData(gold = 5000),
    MissionRewardData(item = listOf(randomReinforceItem(1, ReinforceImportEnum.GENERAL))),
    MissionRewardData(item = listOf(randomReinforceItem(1, ReinforceImportEnum.GENERAL))),
    MissionRewardData(item = listOf(randomReinforceItem(1, ReinforceImportEnum.GENERAL))),
    MissionRewardData(gold = 15_000),
    MissionRewardData(item = listOf(randomReinforceItem(1, ReinforceImportEnum.BASIC))),
    MissionRewardData(crystal = 20)
)