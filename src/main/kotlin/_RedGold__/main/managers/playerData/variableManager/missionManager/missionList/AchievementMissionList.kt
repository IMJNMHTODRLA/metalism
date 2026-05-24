package _RedGold__.main.managers.playerData.variableManager.missionManager.missionList

import _RedGold__.main.managers.playerData.variableManager.missionManager.MissionInfoData
import _RedGold__.main.managers.playerData.variableManager.missionManager.MissionRewardData
import _RedGold__.main.core.gacha.item.skill.reinforceManager.ReinforceImportEnum.INTERMEDIA
import _RedGold__.main.core.gacha.item.skill.reinforceManager.ReinforceImportEnum.ADVANCED
import _RedGold__.main.core.gacha.item.skill.reinforceManager.randomReinforceItem

val achievementMissionInfoList = listOf(
    MissionInfoData("일일 접속 365회", 365, "&a&l[시간의 연속] 칭호 지급, &b&l600 크리스탈 지급"), //완
    MissionInfoData("일일 접속 730회", 730, "&b&l600 크리스탈 지급"), //완
    MissionInfoData("일일 접속 1,095회", 1095, "&b&l1,200 크리스탈 지급, &e&l무작위 고급 강화석 5개 지급"), //완
    MissionInfoData("플레이어 처치 100회", 100, "&b&l600 크리스탈 지급"), //완
    MissionInfoData("플레이어 처치 200회", 200, "&4&l[킬러] 칭호 지급, &b&l600 크리스탈 지급"), //완
    MissionInfoData("월간 상점 아이템 구매 6회", 6, "&b&l600 크리스탈 지급"),
    MissionInfoData("월간 상점 아이템 구매 12회", 12, "&d&l[컬렉션] 칭호 지급"),

    MissionInfoData("블록 설치 2,500회", 2500, "&6&l5,000,000 골드 지급"), //완
    MissionInfoData("블록 설치 5,000회", 5000, "&6&l5,000,000 골드 지급"), //완
    MissionInfoData("블록 설치 10,000회", 10000, "&8&l[무게 변화] 칭호 지급, &b&l240 크리스탈 지급"), //완
    MissionInfoData("블록 파괴 2,500회", 2500, "&6&l5,000,000 골드 지급"), //완
    MissionInfoData("블록 파괴 5,000회", 5000, "&6&l5,000,000 골드 지급"), //완
    MissionInfoData("블록 파괴 10,000회", 10000, "&8&l[지각 변동] 칭호 지급, &b&l240 크리스탈 지급"), //완
    MissionInfoData("엔더진주 사용 300회", 300, "&e&l무작위 중급 강화석 2개 지급"), //완

    MissionInfoData("엔드 수정 폭팔 500회", 500, "&e&l무작위 중급 강화석 2개 지급"), //완
    MissionInfoData("리스폰 정박기 폭팔 500회", 500, "&e&l무작위 중급 강화석 2개 지급"), //완
    MissionInfoData("불사의 토템 발동 500회", 500, "&e&l무작위 중급 강화석 2개 지급"),
    MissionInfoData("황금 사과 섭취 400회", 400, "&e&l무작위 중급 강화석 2개 지급"),
    MissionInfoData("위더 처치 100회", 100, "&e&l무작위 중급 강화석 2개 지급")
)

val achievementMissionRewardList = listOf(
    MissionRewardData(crystal = 600, style = 1),
    MissionRewardData(crystal = 600),
    MissionRewardData(crystal = 1200, item = listOf(randomReinforceItem(5, ADVANCED))),
    MissionRewardData(crystal = 600),
    MissionRewardData(crystal = 600, style = 2),
    MissionRewardData(crystal = 600),
    MissionRewardData(style = 3),

    MissionRewardData(gold = 2_000_000),
    MissionRewardData(gold = 5_000_000),
    MissionRewardData(crystal = 240, style = 4),
    MissionRewardData(gold = 2_000_000),
    MissionRewardData(gold = 5_000_000),
    MissionRewardData(crystal = 240, style = 5),
    MissionRewardData(item = listOf(randomReinforceItem(2, INTERMEDIA))),

    MissionRewardData(item = listOf(randomReinforceItem(2, INTERMEDIA))),
    MissionRewardData(item = listOf(randomReinforceItem(2, INTERMEDIA))),
    MissionRewardData(item = listOf(randomReinforceItem(2, INTERMEDIA))),
    MissionRewardData(item = listOf(randomReinforceItem(2, INTERMEDIA))),
    MissionRewardData(item = listOf(randomReinforceItem(2, INTERMEDIA)))
)