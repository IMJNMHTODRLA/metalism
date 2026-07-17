package _RedGold__.main.managers.playerData.variableManager.missionManager.missionList

import _RedGold__.main.managers.playerData.variableManager.missionManager.MissionInfoData
import _RedGold__.main.managers.playerData.variableManager.missionManager.MissionRewardData
import _RedGold__.main.core.cartridge.upgradeItem.skill.reinforce.ReinforceImportEnum
import _RedGold__.main.core.cartridge.upgradeItem.skill.reinforce.randomReinforceItem

val weeklyMissionInfoList = listOf(
    MissionInfoData("일일 접속 5회", 5, "&6&l20,000 골드 지급"), //완
    MissionInfoData("일일 상점 아이템 구매 5회", 5, "&6&l30,000 골드 지급"), //완
    MissionInfoData("블록 설치 128회", 128, "&6&l10,000 골드 지급"), //완
    MissionInfoData("블록 파괴 128회", 128, "&6&l10,000 골드 지급"), //완
    MissionInfoData("엔티티 처치 40회", 40, "&6&l20,000 골드 지급"), //완
    MissionInfoData("플레이어 처치 5회", 5, "&b&l30 크리스탈 지급"), //완
    MissionInfoData("주간 미션 5회 완료", 5, "&b&l120 크리스탈 지급, &e&l무작위 초급 강화석 2개 지급") //완
)

val weeklyMissionRewardList = listOf(
    MissionRewardData(gold = 20_000),
    MissionRewardData(gold = 30_000),
    MissionRewardData(gold = 10_000),
    MissionRewardData(gold = 10_000),
    MissionRewardData(gold = 20_000),
    MissionRewardData(crystal = 30),
    MissionRewardData(crystal = 120, item = listOf(randomReinforceItem(2, ReinforceImportEnum.BEGINNER)))
)