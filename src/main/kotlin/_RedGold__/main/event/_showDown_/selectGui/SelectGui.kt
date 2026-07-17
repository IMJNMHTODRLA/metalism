package _RedGold__.main.event._showDown_.selectGui

import _RedGold__.main.event._showDown_.DataManager
import _RedGold__.main.event._showDown_.EventMetaDatas
import _RedGold__.main.functions.Color.rgb
import _RedGold__.main.functions.Cubic.then
import _RedGold__.main.functions.Gui.getItem
import _RedGold__.main.function.api.toFormat
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import java.util.UUID

class SelectGui {
    private val  = """
        ${rgb("2444FC")}§l§o[
        ${rgb("2B49FC")}§l§oM
        ${rgb("324DFC")}§l§oE
        ${rgb("3952FD")}§l§oT
        ${rgb("4057FD")}§l§oA
        ${rgb("475CFD")}§l§oL
        ${rgb("4E60FD")}§l§oI
        ${rgb("5565FD")}§l§oS
        ${rgb("5B6AFE")}§l§oM 
        ${rgb("6973FE")}§l§oE
        ${rgb("7078FE")}§l§oV
        ${rgb("777DFE")}§l§oE
        ${rgb("7E82FF")}§l§oN
        ${rgb("8586FF")}§l§oT
        ${rgb("8C8BFF")}§l§o]
    """.trimIndent().replace("\n", "")

    private fun cashingMessage(uuid: UUID, cashingPoint: String, difficulty: Int): String {
        return if (cashingPoint == "-" || DataManager.cachingDifficulty[uuid] != difficulty) {
            "&c&l해당 난이도를 최소 한 번 클리어해야 합니다."
        } else {
            "&f&l대결전 티켓 1장과 50,000 골드를 사용하여 ${cashingPoint}점으로 소탕이 가능합니다."
        }
    }

    private fun Inventory.fi(n: Int, id: Int, uuid: UUID, cashingPoint: String, isEnchant: Boolean = false) {
        val guiMetadata = EventMetaDatas.GuiMetaData.entries[id]
        val minionMetaData = EventMetaDatas.MinionMetaData.entries[id]
        val bossMetadata = EventMetaDatas.BossMetaData.entries[id]
        val pointMetaData = EventMetaDatas.PointMetaData.entries[id]
        val coinMetaData = EventMetaDatas.CoinMetaData.entries[id]

        this.setItem(n, getItem(
            guiMetadata.difficultyItem,
            "${guiMetadata.difficultyName} &e&l대결전 티켓이 ${DataManager.ticket[uuid]?: 0}장 남았습니다.",
            listOf(
                "&f&l[선택(좌클릭)] &f&l클리어 시 소탕이 가능합니다.",
                "&8&l(클리어 시 기존에 클리어 한 난이도에서 소탕이 불가능 합니다.)",
                "&f&l[소탕(우클릭)] ${cashingMessage(uuid, cashingPoint, id)}",
                "",
                "&c&l플레이어들이 못 찾아오고 기지 또는 스폰 구역으로 부터",
                "&c&l멀리 떨어져 있는 곳에서 PVE를 하시는 걸 권장합니다.",
                "",
                "&c&l대결전 중 퇴장 시 티켓은 반환되지 않으며,",
                "&c&l지금까지 입힌 피해량만큼 체력 점수만 지급됩니다."
            )
        ).apply {
            isEnchant then addItemFlags(ItemFlag.HIDE_ENCHANTS); addUnsafeEnchantment(Enchantment.SHARPNESS, 8)
        })

        this.setItem(n + 9, getItem(
            "emerald",
            "&8&l[&a&l점수 및 보상(${guiMetadata.difficultySimple})&8&l]",
            listOf(
                "&f&l대결전 코인: &d&l${coinMetaData.commonCoin} 코인",
                "&f&l고급 대결전 코인: &d&l${coinMetaData.advancedCoin} 고급 코인",
                "",
                "&f&l클리어 점수: &d&l최대 ${pointMetaData.clearPoint.toFormat()}점&8&l(보스 처치 시 지급)",
                "&f&l체력 점수: &d&l최대 ${pointMetaData.healthPoint.toFormat()}점&8&l(1 대미지 당 ${EventMetaDatas.DAMAGE_HEALTH_POINT}점 씩 지급)",
                "&f&l시간 점수: &d&l최대 ${pointMetaData.timePoint.toFormat()}점&8&l(매 초당 ${pointMetaData.secTimePoint.toFormat()}점 씩 차감)",
                "",
                "&e&l총합 최대 점수: &d&l${pointMetaData.totalPoint.toFormat()}점"
            )
        ))

        this.setItem(n + 18, getItem(
            "book",
            "&8&l[&c&l보스 정보(${guiMetadata.difficultySimple})&8&l]",
            listOf(
                "&f&l체력: &c&l${bossMetadata.hp.toFormat(1)}",
                "&f&l방어력: &b&l${bossMetadata.defense.toFormat(1)}",
                "&f&l대미지: &4&l${bossMetadata.damage.toFormat(1)}",
                "&8&l모든 스킬 설명은 플레이어의 시점입니다.",
                "",
                "&f&l[기본 스킬&c&l(Hard ↑)&f&l]: 보스에게 입힌 피해의 8%를 받습니다.", //완
                "&f&l[강화 스킬&4&l(Extreme ↑)&f&l]: 보스의 공격력 +15%/미니언의 공격력 +5%", //완
                "",
                "&4&l[EX 스킬]&f&l: &c&l10초간 플레이어의 허기를 35 가져갑니다.", //완
                "&7- &f&l포만감 수치가 0초과일 시 최대 120%까지 대미지를 더 입힐 수 있습니다.", //완
                "",
                "&4&l[EX 스킬]&f&l: &c&l좀비 미니언 5마리&f&l를 소환합니다.", //완
                "&7- &f&l미니언 생존 시 입힌 피해가 &c&l반감&f&l되며", //완
                "&7- &f&l입힌 피해의 &c&l50%&f&l를 받습니다.", //완
                "&7- &f&l미니언은 받은 피해를 반감 없이 보스에게 전달합니다.", //완
                "&7- &f&l미니언은 회복이 불가능하며 만일 EX스킬 발동 이후에", //완
                "&7- &f&l미니언이 남아 있다면 남아있는 미니언의 체력 만큼 보스가 회복합니다.", //완
                "",
                "&f&l[미니언 처치 정보&7&l(체력: ${minionMetaData.hp.toFormat(1)} / 대미지: ${minionMetaData.damage.toFormat(1)})]",
                "&7- &f&l1마리 처치: 보스에게 미니언의 최대 체력의 150% 고정 피해", //완
                "&7- &f&l개별 처치 버프: &4&l힘 I(10초) &f&l/ &e&l흡수 I(20초)", //완
                "&7- &6&l모두 처치 시: &c&l300% &f&l추가 고정 피해 + &c&l힘 II(10초)", //완
            )
        ))

        /*
        this.setItem(n + 18, getItem(
            "book",
            "&8&l[&c&l보스 정보(${guiMetadata.difficultySimple})&8&l]",
            listOf(
                "&f&l체력: &c&l${bossMetadata.hp.toFormat(1)}",
                "&f&l방어력: &b&l${bossMetadata.defense.toFormat(1)}",
                "&f&l대미지: &4&l${bossMetadata.damage.toFormat(1)}",
                "&8&l모든 스킬 설명은 플레이어의 시점입니다.",
                "",
                "&f&l[기본 스킬&c&l(Hard ↑)&f&l]: 보스에게 입힌 피해의 8%를 반사 받습니다.",
                "&f&l[강화 스킬&4&l(Extreme ↑)&f&l]: 보스 공격력 +15% / 미니언 공격력 +5%",
                "",
                "&4&l[EX 스킬 - 영양 약탈]&f&l: &c&l10초간 플레이어의 허기를 총 35 흡수합니다.",
                "&7- &f&l대미지 효율은 &e&l포만감&f&l과 &6&l허기&f&l 수치에 정비례합니다.",
                "&7- &f&l포만감 0 초과 시: &b&l포만감 1당 공격력 +1%",
                "&7- &f&l포만감 0 미만 시: &7&l허기 1감소당 공격력 -1%",
                "",
                "&4&l[EX 스킬 - 군단 소환]&f&l: &c&l좀비 미니언 5마리&f&l를 소환합니다.",
                "&7- &f&l미니언 생존 시 본체 타격 대미지가 &c&l99.9% (<- 이건 Torment이상, 미만은 60%) 감소되며&f&l,",
                "&7- &f&l입힌 피해의 &c&l50% <- (이건 Torment미만, Torment이상은 85%)&f&l를 반사 받습니다.",
                "&7- &6&l[특수]&f&l 미니언 방어력은 플레이어의 &e&l포만감&f&l이 높을수록 강화됩니다. <- (이건 인세인 이상)",
                "&7  &8(배가 부를수록 미니언을 통한 보스 타격이 더욱 어려워집니다.)",
                "",
                "&f&l[미니언 처치 보상&7&l(HP: ${minionMetaData.hp.toFormat(1)})]",
                "&7- &f&l1마리 처치: 보스에게 미니언 최대 HP의 150% 고정 피해 <- (루나틱 이후로는 플레이어의 체력이 50%깎이고 보스에게 최대 HP의 300%지급)",
                "&7- &f&l개별 처치 버프: &4&l힘 I(10초) &f&l/ &e&l흡수 I(20초) <- (인세인 이후로는 힘 II(15초)와 체력 2.5칸 깎임)",
                "&7- &6&l모두 처치 시: &c&l300% &f&l추가 고정 피해 + &c&l힘 II(10초) <- (토먼트 이후로는 666%로 되고 나약함 I(60초) 지급)",
                "",
                "&8&l※ 미니언 생존 시 보스는 남은 미니언의 HP만큼 회복합니다. <- (루나틱 이후로는 1.5배 만큼 HP 회복)"
            )
        ))
        + Extreme 이후로는
        "&4&l[강화 스킬 - 기회는 오직 한 번]&f&l: &c&l불사의 토템 사용을 금지합니다.",
        "&7- &f&l단 포만감이 19.8 이상일 때 불사의 토템 사용 될 시",
        "&7- &f&l불사의 토템이 사용되며, 포만감 25가 감소되며 재생 V 8초, 힘 V 10초가 지급됩니다.(전투당 한 번)",

        PVE: 대결전(1st)
        PVP: 상수변동전(2nd)
        100층 시스템: 수치임계돌파전(3rd)
        미니 컨텐츠: (4th)
        //PVE에서 져도 아이템 안 떨궈짐

        기본 적으로 참여시 10만점 지급
        재시작 전까지 한번이라도 참여자랑 PVP 안 했을 시 점수에서 5%차감
        pvp시 공격 위치로부터 +-20블록 거리로 필드 생성
        필드로 나가는 엔티티가 있을 시 엔티티를 땅으로 추락
        필드 내로 들어올려는 플레이어는 캔슬(만일 이미 필드에 있는 플레이어는 중심부로 부터 뒤로 밀어짐
        필드 내로 들어올려는 projective는 땅으로 추락
        15초 동안 둘다 공격 안할 시 필드 삭제
        공격자나 피해자는 그 둘 외에는 다른 플레이어가 공격 못함
        처치 시(공격자가 처치하거나 피해자가 처치하거나 상관 없음) 공격자는 20%가 때와지고, 피해자는 18% 가져와진다
        티켓 1장 사용됨 그러면

        티켓은 매일 3장씩 지급, 점수 매기는 방식은 가져온 점수 중에서 최대 점수 즉
        A플레이어에게 50점을 가져왔으면 1만점 + 50점이 최대 점수, 누적은 1만점 + 50
        B플레이어를 잡고 20점을 가져왔으면 1만점 + 50점이 최대 점수, 누적은 1만점 + 50 + 20
        c플레이어를 잡고 200점을 가져왔으면 1만점 + 200점이 최대 점수, 누적은 1만점 + 50 + 20 + 200

        랭킹은 최고 점수로 하는데 최고점수로 크리스탈 받지만 현재 점수(얘는 줄어들 수도 있음)는 점수 비례로 골드 지급

        //minecraft:block.respawn_anchor.charge






        🧩 1️⃣ 서버 기본 방향

        컨셉: 반야생 약탈 서버

        목표 동접: 100

        Velocity로 대기열 + 메인 서버 분리

        시즌형 콘텐츠 중심 운영

        기믹 이해 중심 PvE 지향 (장비빨 게임 X)

        🐉 2️⃣ 월간 콘텐츠 구조
        ✅ 1주차: 보스전 (랭킹 있음)

        난이도 선택 가능 (EASY ~ EXTREME)

        점수 시스템:

        클리어 점수

        체력 점수

        시간 점수

        최고 점수만 랭킹 반영

        상위 난이도 클리어 시 플레티넘 확정 구조

        보스는 2~4개월 로테이션
        밸런스 수정 후 재등장 가능

        ✅ 2주차: PVP 시즌

        기간 한정 진행

        랭킹 있음

        티어 보상 지급

        ✅ 3주차: 100층 솔플 엔드 콘텐츠

        솔로 전용

        25층 단위 구간 개방

        업데이트된 층까지만 도전 가능

        100층은 매달 리셋

        최초 1회 보상

        80층까지는 강화 재화 충분히 지급

        80층 이후는 소량 지급

        145층 같은 확장층은 칭호만 지급

        칭호 예:
        [(년도). (보스 이름) 100층 돌파!!]

        랭킹은 표시만, 보상 없음

        ✅ 4주차: 휴식
        💰 3️⃣ 가챠 시스템

        1크리스탈 = 25루비

        10연 = 1200 크리스탈

        천장 = 24000 크리스탈

        티어별 보상 600~1200 크리스탈

        누적 점수 최대 600 크리스탈 추가

        🎉 6개월마다 페스 가챠 (2주)

        확률 2배 (3% → 6%)

        한계돌파 재화 확률 2배

        천장 200포인트 (10연 20번)

        이전 페스 아이템도 등장

        하지만 천장 교환은 이번 페스 아이템만 가능

        ⚔ 4️⃣ 보스 설계 방향
        기본 철학:

        전투 기믹 이해 중심 게임

        장비 메타 게임 X
        상성 150% 억까 구조 X

        🧠 핵심 시스템
        허기 시스템 변경

        허기 = 생존 리소스

        포만감 = 공격 리소스

        허기 풀 = 기본 100%

        포만감 1당 +1% 피해 증가

        예:

        포만감 20 → 120%

        포만감 40 → 140%

        🔥 페스 예시 아이템

        효과:

        30초 과포화 (포만감 폭증)

        포화 V

        속도 감소 II

        최대 체력 30% 감소

        30초간 직접 피해 110%

        이후 40초간 직접 피해 90%

        쿨타임 5분

        → 리스크형 극딜 타이밍 생성 아이템
        → 필수템이 아니라 숙련자용 선택지

        👹 보스 기믹 특징

        피해 반사

        미니언 생존 시 본체 피해 감소

        미니언 처치 시 고정 피해

        허기/포만감 기반 피해 증폭

        EX 스킬 확장 가능

        향후 공격 타입 / 방어 타입 추가 예정 (EXTREME부터)

        📊 5️⃣ 설계 방향성 요약

        너 서버는:

        ❌ 단순 반야생 서버 아님
        ❌ 장비빨 PvP 서버 아님
        ❌ 숫자 인플레 게임 아님

        ✅ 시즌형 기믹 이해 PvE 중심 서버
        ✅ 리스크-보상 설계
        ✅ 숙련자 보상형 구조
        ✅ FOMO는 있지만 강제는 아님
        */
    }

    fun openGui(player: Player, sound: Float = 1f) {
        val gui = SelectHolder().inventory
        val uuid = player.uniqueId

        for (i in 0 until gui.size) gui.setItem(i, getItem("magenta_stained_glass_pane", prefix))

        val cashingPoint = (DataManager.cachingPoint[uuid]?: 0).takeIf{it > 0}?.toFormat()?: "-"
        for (i in 0..4) gui.fi(i + 11, i, uuid, cashingPoint)

        player.openInventory(gui)
        player.playSound(player.location, Sound.BLOCK_END_PORTAL_FRAME_FILL, sound, 1f)
    }
}