package _RedGold__.main.event._showDown_

import _RedGold__.main.event._showDown_.managers.BossSession
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object DataManager {
    val START_TIME = LocalDateTime.of(2025, 12, 1, 11, 0, 0)
    val END_TIME = LocalDateTime.of(3025, 12, 8, 3, 59, 0)

    const val BATTLE_ID = "DE46FF1C"
    const val BATTLE_ID_SUB = "56B10386"

    const val SEASON_ID = "00_00"
    const val SHOW_DOWN_PATH = "show_down/$SEASON_ID"

    const val POINT_PATH = "$SHOW_DOWN_PATH/point"
    const val BEST_POINT_PATH = "$SHOW_DOWN_PATH/best_point"
    const val CACHE_DIFFICULTY_PATH = "$SHOW_DOWN_PATH/cache_difficulty"
    const val CACHE_POINT_PATH = "$SHOW_DOWN_PATH/cache_point"
    const val TICKET_PATH = "$SHOW_DOWN_PATH/ticket"

    const val OWNER_UUID = "OWNER_UUID"

    const val TYPE_HANDEL = "BATTLE_TYPE"
    const val TYPE_BOSS = "TYPE_BOSS_$BATTLE_ID"
    const val TYPE_MINION = "TYPE_MINION_${BATTLE_ID}_$BATTLE_ID_SUB"

    const val BOSS_NAME = "𝐎𝐁𝐄𝐑𝐎𝐍"
    const val BOSS_LORE = "\uD835\uDE77\uD835\uDE8E\uD835\uDE9B\uD835\uDE98\uD835\uDE8B\uD835\uDE9B\uD835\uDE92\uD835\uDE97\uD835\uDE8E"
    const val BOSS_NAME_ASCII = "OBERON"
    const val BOSS_NAME_KR = "오베론"

    val story = listOf(
        "아주 오래전, 마인크래프트 데이터 깊은 곳에는 '금지된 기록($BOSS_LORE)'이 있었습니다.",
        "수호자 ${BOSS_NAME_KR}는 세상이 혼란에 빠지지 않도록 그 기록을 봉인해 왔습니다.",
        "하지만 기나긴 세월 속에 봉인은 낡았고, 기록자였던 ${BOSS_NAME_KR}마저 그 사악한 힘에 잠식되고 말았습니다.",
        "${BOSS_NAME}을 장악한 ${BOSS_LORE}은 이제 봉인되었던 '금지된 기록($BOSS_LORE)'을 현실에 써 내려가려 합니다.",
        "당신은 ${BOSS_LORE}에게 잠식된 ${BOSS_NAME_KR}을 오염의 힘에서 구원하여 그 공포를 걷어내고,",
        "이 세계의 진정한 역사를 다시 써 내려가야 합니다. 그럼 행운을..."
    )

    //val story = listOf(
    //    "아주 오래전, 마인크래프트 데이터 깊은 곳에는 '금지된 기록($BOSS_LORE)'이 존재했습니다.",
    //    "수호자 ${BOSS_NAME_KR}은 그 기록이 세상에 퍼지지 않도록 오랫동안 봉인해 왔습니다.",
    //    "하지만 긴 세월 속에서 봉인은 약해졌고, ${BOSS_NAME_KR}마저 그 힘에 잠식되고 말았습니다.",
    //    "이제 ${BOSS_LORE}은 ${BOSS_NAME}을 통해 금지된 기록을 현실에 다시 쓰려 합니다.",
    //    "당신은 타락한 ${BOSS_NAME_KR}을 오염에서 해방시키고, 이 세계의 역사를 지켜야 합니다.",
    //    "준비하십시오… ${BOSS_NAME}이 깨어납니다."
    //)

    val battleSession = ConcurrentHashMap<UUID, BossSession>()

    //val isPlay = ConcurrentHashMap<UUID, Boolean>()

    //val difficulty = ConcurrentHashMap<UUID, Int>()
    //val time = ConcurrentHashMap<UUID, Long>()
    //val bossMinionEntities = ConcurrentHashMap<UUID, Set<LivingEntity>>()

    val point = ConcurrentHashMap<UUID, Long>()
    val bestPoint = ConcurrentHashMap<UUID, Long>()

    val commonCoin = ConcurrentHashMap<UUID, Int>() //coin/common
    val advancedCoin = ConcurrentHashMap<UUID, Int>() //coin/advanced

    val ticket = ConcurrentHashMap<UUID, Int>()

    val cachingPoint = ConcurrentHashMap<UUID, Long>() //소탕 기능
    val cachingDifficulty = ConcurrentHashMap<UUID, Int>() //소탕 기능
}