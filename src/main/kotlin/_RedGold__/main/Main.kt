package _RedGold__.main

import _RedGold__.main.functions.task
import _RedGold__.main.loads.FinalFlush
import _RedGold__.main.loads.PreLoad
import _RedGold__.main.loads.SlowInit
import _RedGold__.main.managers.InitManager
import _RedGold__.main.managers.rebootManager.RebootManager
import com.github.retrooper.packetevents.PacketEvents
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder
import org.bukkit.plugin.java.JavaPlugin
import java.time.LocalDateTime

class Main : JavaPlugin() {
    /*object Event {
        const val EVENT_NAME = "&b&l대결전(PVE)"
        const val EVENT_CODE = "randomEffect"
        const val EVENT_ITEM = "diamond_sword"
        val START_TIME: LocalDateTime = LocalDateTime.of(2025, 12, 7, 12, 0, 0)
        val END_TIME: LocalDateTime = LocalDateTime.of(3025, 12, 13, 10, 0, 1)
    }

    object Gacha {
        const val IS_LIMITED = false
        const val GACHA_MESSAGE = "상시 뽑기"
        val gachaPercent = listOf(
            0.5, 0.75, 1.0,
            1.5, 1.5, 46.0, 48.75
        )
        //val gachaPercent = listOf(
        //    0.75, 1.0, 1.25,
        //    2.0, 2.0, 46.0, 47.0
        //) //패스 가챠 때
        const val GACHA_POINT_TO_GOLD_TIMES = 0L
    }*/

    companion object {
        private lateinit var rebootManager: RebootManager
        lateinit var instance: Main
            private set
        //lateinit var pvpmanager: PvPManager
        //    private set
        //lateinit var server: Server
        //    private set
    }

    //TODO: 모든 Gui 클래스를 object로 변경

    //TODO: 카트리지에는 대미지 양이 적혀져 있지 않고 그냥 스킬 레벨 그런게 몇렙인지 적혀 있어야 한다ㅇㅇ
    //TODO: 기초 대미지 량이나 그런거는 서버 side로, 그리고 최종 대미지 량도 서버 side ㅇㅇ
    //TODO: 카트리지 과부화 라는 것을 장착 시 최종 능력치에서 1.2배 곱해짐, 카트리지 과부화는 MVP 랭크만 24000 크리스탈로 장착 가능

    //TODO: 플레이어 체력 기본적으로 40으로 설정 할까?
    /*
    * TODO: a, b, c 아이템을 쓰면 쿨이 한개라도 지나기 전 까지는 더이상 스킬 발동 못함
    *  쿨 지난 아이템 갯수 만큼 더 쓸 수 있음
    *  이름은 QUEUE으로 해야겠다
    *
    * [1. 방어 속성 시스템의 핵심 규칙]
    * - 유저가 카트리지(무기)을 인벤토리에 개수 제한 없이 들고 다닐 수 있고, 평소에는 속성이 없는 바닐라 검을 들고 싸울 수 있는 환경을 고려함.
    * - 밸런스 붕괴와 실시간 장비 스왑 꼼수를 막기 위해, 플레이어 본체의 방어 속성은 인벤토리 전체가 아니라 '현재 발동되어 쿨타임이 돌아가고 있는 EX 스킬 슬롯(QUEUE, 최대 3개)'에 등록된 아이템들의 속성을 기준으로 결정함.
    *
    * [2. 시간대 적성 시스템 및 등급]
    * - 마인크래프트 시간대에 따라 조간, 주간, 석간, 야간 4가지로 분류함.
    * - 각 카트리지마다 특정 시간대에 발동 시 적용되는 적성 등급(S, A, B, C, D)이 존재하며, 보스전의 경우 보스방의 시간대가 특정 시간으로 아예 고정되어 있음.
    * - 등급별 능력치 스펙:
    *   * S등급: 공격력 130%
    *   * A등급: 공격력 115%
    *   * B등급: 공격력 100%
    *   * C등급: 공격력 85%
    *   * D등급: 공격력 70%
    *
    * [3. 최종 대미지 계산 공식]
    * - 최종 공격 공식 (곱연산):
    *   기본 공격력 * 공격 속성 * 시간대 공격력
    *   * 예시: 공격 속성 상성이 200%이고 시간대 적성이 S등급(130%)이면, 최종 공격 공식은 공격력 * 2.0 * 1.3 = 공격력 * 2.6 (260%)이 됨.
    *
    * - 최종 방어 배율 공식 (평균값 및 피감 적용):
    *   QUEUE에 들어있는 모든 EX 스킬의 방어 속성 %의 합: x
    *   QUEUE에 들어있는 EX 스킬 개수: y
    *   x / y
    *   * 단, 방어 속성 평균값은 최소 50%(0.5)에서 최대 200%(2.0) 사이로 작동함.
    *   * 만약 QUEUE가 비어있어 스킬 개수가 0개일 때는 방어 속성 평균값의 기본값인 100%(1.0)를 적용하여 계산함.
    *
    * TODO: 스킬 업글 관련
    *  Reinforce는 종류가 5가지(그 안에 일반, 하급, 초급, 중급, 고급)
    *  plasmaCore는 종류가 3가지(그 안에 저에너지, 중에너지, 중에너지)
    *  mystery는 종류가 10가지(그 안에 파괴된, 손실된, 복구된, 온전한)
    *  종류 좀 설명하자면
    *  공허, 심연, 찰나, 망각, 왜곡, 파동, 격류, 잔영, 맹약, 계시, 환상, 비명, 성흔, 금제, 궤적
    *  공허, 기억, 의지, 파편, 심장, 정수, 혈흔, 눈물, 그림자, 잔향, 금기, 기록, 공명, 침식, 낙인
    *  이렇게임
    *  -
    *  -
    *  1~3스킬:
    *   1레벨:
    *    5,000골드, [일반] xx(회복) 강화 아이템x5, [저에너지] yy(폭팔) 플라즈마 코어x3
    *   2레벨:
    *    7,500골드, [일반] xx(회복) 강화 아이템x8, [저에너지] yy(폭팔) 플라즈마 코어x6
    *   3레벨:
    *    50,000골드, [하급] xx(회복) 강화 아이템x5, [저에너지] yy(폭팔) 플라즈마 코어x3, 파괴된 zz(공허)의 신비x8, 파괴된 aa(공허 외)의 신비x6
    *   4레벨:
    *    80,000골드, [하급] xx(회복) 강화 아이템x8, [저에너지] yy(폭팔) 플라즈마 코어x6, 파괴된 zz(공허)의 신비x10, 파괴된 aa(공허 외)의 신비x8
    *   5레벨:
    *    250,000골드, [초급] xx(회복) 강화 아이템x5, [중에너지] yy(폭팔) 플라즈마 코어x3, 손실된 zz(공허)의 신비x6, 손실된 aa(공허 외)의 신비x4
    *   6레벨:
    *    300,000골드, [초급] xx(회복) 강화 아이템x8, [중에너지] yy(폭팔) 플라즈마 코어x6, 손실된 zz(공허)의 신비x8, 손실된 aa(공허 외)의 신비x6
    *   7레벨:
    *    700,000골드, [중급] xx(회복) 강화 아이템x5, [중에너지] yy(폭팔) 플라즈마 코어x3, 복구된 zz(공허)의 신비x4, 복구된 aa(공허 외)의 신비x2
    *   8레벨:
    *    1,150,000골드, [중급] xx(회복) 강화 아이템x8, [중에너지] yy(폭팔) 플라즈마 코어x6, 복구된 zz(공허)의 신비x6, 복구된 aa(공허 외)의 신비x4
    *   9레벨:
    *    2,850,000골드, [고급] xx(회복) 강화 아이템x8, [고에너지] yy(폭팔) 플라즈마 코어x6, 보존된 zz(공허)의 신비x2
    *   10레벨:
    *    3,200,000골드, [고급] xx(회복) 강화 아이템x12, [고에너지] yy(폭팔) 플라즈마 코어x12, 보존된 zz(공허)의 신비x4
    *  EX스킬:
    *   1레벨:
    *    8,000골드, [일반] xx(회복) 강화 아이템x8, [저에너지] yy(폭팔) 플라즈마 코어x4,
    *   2레벨:
    *    12,000골드, [일반] xx(회복) 강화 아이템x10, [저에너지] yy(폭팔) 플라즈마 코어x6,
    *   3레벨:
    *    60,000골드, [하급] xx(회복) 강화 아이템x8, [저에너지] yy(폭팔) 플라즈마 코어x4,
    *   4레벨:
    *    110,000골드, [하급] xx(회복) 강화 아이템x10, [저에너지] yy(폭팔) 플라즈마 코어x6,
    *   5레벨:
    *    300,000골드, [초급] xx(회복) 강화 아이템x8, [중에너지] yy(폭팔) 플라즈마 코어x4,
    *   6레벨:
    *    700,000골드, [초급] xx(회복) 강화 아이템x10, [중에너지] yy(폭팔) 플라즈마 코어x6,
    *   7레벨:
    *    1,450,000골드, [중급] xx(회복) 강화 아이템x8, [중에너지] yy(폭팔) 플라즈마 코어x4, 복구된 zz(공허)의 신비x6
    *   8레벨:
    *    2,300,000골드, [중급] xx(회복) 강화 아이템x10, [중에너지] yy(폭팔) 플라즈마 코어x6, 복구된 zz(공허)의 신비x8
    *   9레벨:
    *    3,800,000골드, [고급] xx(회복) 강화 아이템x10, [고에너지] yy(폭팔) 플라즈마 코어x6, 온전한 zz(공허)의 신비x4
    *   10레벨:
    *    4,500,000골드, [고급] xx(회복) 강화 아이템x14, [고에너지] yy(폭팔) 플라즈마 코어x12, 온전한 zz(공허)의 신비x6
    *
    * TODO:
    *  속성 형태:
    *             [일반]  [폭팔(Explosion)]  [화염(Fire)]  [마법(Magic)]
    *  [일반 방어]  100%   100%   100%    100%
    *  [중갑 방어]  100%   200%   100%    50%
    *  [가연 방어]  100%   50%    200%    100%
    *  [경질 방어]  100%   100%   50%     200%
    *  처음부터 50%그거 하는게 아니라 업글 형식, 처음에는 저항 그게 100%임, 최대 20레벨임
    *  ----
    *  기본 20체력에서 플레이어가 업글해서 최대 체력 늘릴 수 있음(1레벨당 1체력(0.5칸), 필요 기초 재화: 400,000골드(1.2배씩 증가), MAX 20렙) <- 없앨까 싶음
    *  기본 1 공격력에서 플레이어가 업글해서 기본 공격력 늘릴 수 있음(1레벨당 1공격력(0.5칸), 필요 기초 재화: 500,000골드(1.3배씩 증가), MAX 20렙) <- 없앨까 싶음
    *  기본 80틱 자연회복에서 플레이어가 업글해서 자연회복 속도 줄일 수 있음(1레벨당 1틱(0.25초), 필요 기초 재화: 300,000골드(1.1배씩 증가), MAX 20렙) <- 없앨까 싶음
    * */

    override fun onLoad() {
        instance = this

        PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this))
        PacketEvents.getAPI().settings.checkForUpdates(false).bStats(true)
        PacketEvents.getAPI().load()
    }

    override fun onEnable() {
        //instance = this
        //pvpmanager = Bukkit.getPluginManager().getPlugin("PvPManager") as? PvPManager?:
        //    throw PluginException("PvPManager 찾는데 실패", ExceptionSeverity.SHUTDOWN)

        rebootManager = RebootManager(this)

        PacketEvents.getAPI().init()

        InitManager(this).init()
        PreLoad(this).load()

        SlowInit().init()
    }

    /**
    *
    * ### 여기서는 무조건 메인 스레드만 사용!
    * **비동기 스레드 사용하면 종료 작업 완료되지 않고 바로 삭제됨ㅇㅇ**
    *
    * */
    override fun onDisable() {
        FinalFlush().init()

        rebootManager.stop()
    }
}
