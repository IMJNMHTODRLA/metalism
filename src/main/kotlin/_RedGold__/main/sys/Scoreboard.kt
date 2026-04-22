package _RedGold__.main.sys

import _RedGold__.main.event.showDown.System.RandomEffectEvent.nextEvent
import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.Color.rgb
import _RedGold__.main.function.Rank.getPlayerRank
import _RedGold__.main.function.Rank.getPlayerRankPrefix
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.sys.Chat.ChatApply.applyStyle
import _RedGold__.main.sys.Chat.ChatApply.symmetry
import _RedGold__.main.sys.RunScoreboard.PlayerDataCache
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Scoreboard
import java.util.*

class Scoreboard {
    private val rankList: Map<String, Int> = java.util.Map.of(
        "OWNER", 1,
        "ADMIN", 2,
        "PLUS", 3,
        "USER", 99
    )

    private val colorList: Map<Int, String> = java.util.Map.of(
        0, "2444FC",
        1, "334EFC",
        2, "4258FD",
        3, "5162FD",
        4, "5F6DFE",
        5, "6E77FE",
        6, "7D81FF",
        7, "8C8BFF"
    )

    private fun getRankTeamName(rank: String): String {
        var rank = rank
        rank = rank.uppercase(Locale.getDefault())

        val priority = rankList.getOrDefault(rank, 99)
        return "${priority}_$rank"
    }

    private fun syncAllTeamTags(targetPlayer: Player) {
        val personalBoard = targetPlayer.scoreboard

        for (other in Bukkit.getOnlinePlayers()) {
            val teamName = getRankTeamName(getPlayerRank(other))
            val uuid = other.uniqueId
            val playerStyle = applyStyle[uuid]?: -1
            val playerStylePrefix =
                if (playerStyle == -1) ""
                else "${gc(symmetry[playerStyle])} "
            val rankPrefix = "${gc(getPlayerRankPrefix(other))} "
            val name = other.name
            val rankColor = ChatColor.getByChar(rankPrefix.substring(0, 2).replace("§", ""))!!

            var personalTeam = personalBoard.getTeam(teamName)
            if (personalTeam == null) {
                personalTeam = personalBoard.registerNewTeam(teamName)
                personalTeam.prefix = "$playerStylePrefix$rankPrefix"
                personalTeam.color = rankColor

                other.setPlayerListName("$playerStylePrefix$rankPrefix$name")
            }
            personalTeam.addEntry(name)
        }
    }

    private fun Objective.s(msg: String, rank: Int) {
        this.getScore(gc(msg)).score = rank
    }
    
    fun scoreboard(player: Player, count: Int) {
        val teamName = getRankTeamName(getPlayerRank(player))
        val playerName = player.name
        val uuid = player.uniqueId
        val playerStyle = applyStyle[uuid]?: -1
        val playerStylePrefix =
            if (playerStyle == -1) ""
            else "${gc(symmetry[playerStyle])} "
        val rankPrefix = "${gc(getPlayerRankPrefix(player))} "
        val rankColor = ChatColor.getByChar(rankPrefix.substring(0, 2).replace("§", ""))!!

        // 메인 보드 처리
        val mainBoard = Bukkit.getScoreboardManager().mainScoreboard
        var team = mainBoard.getTeam(teamName)
        team?.unregister()

        team = mainBoard.registerNewTeam(teamName)
        team.prefix = "$playerStylePrefix$rankPrefix"
        team.color = rankColor
        team.addEntry(playerName)

        val personalBoard = Bukkit.getScoreboardManager().newScoreboard
        val personalTeam = personalBoard.registerNewTeam(teamName)

        personalTeam.prefix = "$playerStylePrefix$rankPrefix"
        personalTeam.color = rankColor
        personalTeam.addEntry(playerName)

        player.setPlayerListName("$playerStylePrefix$rankPrefix$playerName")

        val sidebar = personalBoard.registerNewObjective(
            "sidebar",
            "dummy",
            """
                ${rgb("2444FC")}§l§oM
                ${rgb("334EFC")}§l§oE
                ${rgb("4258FD")}§l§oT
                ${rgb("5162FD")}§l§oA
                ${rgb("5F6DFE")}§l§oL
                ${rgb("6E77FE")}§l§oI
                ${rgb("7D81FF")}§l§oS
                ${rgb("8C8BFF")}§l§oM
            """.trimIndent().replace("\n", "")
        )
        sidebar.displaySlot = DisplaySlot.SIDEBAR

        val title = """
            ${rgb(colorList[(count + 0) % 8]!!)}§l§oM
            ${rgb(colorList[(count + 1) % 8]!!)}§l§oE
            ${rgb(colorList[(count + 2) % 8]!!)}§l§oT
            ${rgb(colorList[(count + 3) % 8]!!)}§l§oA
            ${rgb(colorList[(count + 4) % 8]!!)}§l§oL
            ${rgb(colorList[(count + 5) % 8]!!)}§l§oI
            ${rgb(colorList[(count + 6) % 8]!!)}§l§oS
            ${rgb(colorList[(count + 7) % 8]!!)}§l§oM
        """.trimIndent().replace("\n", "")

        sidebar.displayName = title

        val gold = PlayerDataCache.gold[uuid]?: 0L
        val cash = PlayerDataCache.cash[uuid]?: 0L
        val kill = PlayerDataCache.kill[uuid]?: 0L
        val death = PlayerDataCache.death[uuid]?: 0L

        val nextEventTime = nextEvent - (System.currentTimeMillis() / 1000)

        sidebar.s("&f&l플레이어: $playerStylePrefix$rankPrefix$playerName", 99)
        sidebar.s("", 98)
        sidebar.s("&f&l골드: &6&l${gold.toFormat()} 골드", 97)
        sidebar.s("&f&l캐시: &b&l${cash.toFormat()} 캐시", 96)
        sidebar.s(" ", 95)
        sidebar.s("  ", 94)
        sidebar.s("&f&l처치 수: &a&l${kill.toFormat()}", 93)
        sidebar.s("&f&l사망 수: &c&l${death.toFormat()}", 92)
        sidebar.s("   ", 91)
        sidebar.s("&f&l추천: &c&lX", 90)
        sidebar.s("    ", 89)
        if (nextEvent != -1L) sidebar.s("&8다음 난이도 선택까지 ${nextEventTime}초", 88)
        else sidebar.s("&8이벤트가 종료 되었습니다.", 87)
        sidebar.s("&8metalism.kro.kr | ${player.ping} ms", 86)

        player.scoreboard = personalBoard
        syncAllTeamTags(player)
    }
}

class Scoreboard1 {
    // 플레이어별 스코어보드 캐시 (깜빡임 방지 및 메모리 관리)
    private val boards = mutableMapOf<UUID, org.bukkit.scoreboard.Scoreboard>()

    // 점수 갱신 시 이전 텍스트를 추적하기 위한 맵 (기존 점수 제거용)
    private val lastLines = mutableMapOf<UUID, MutableMap<Int, String>>()

    private val rankList: Map<String, Int> = mapOf(
        "OWNER" to 1,
        "ADMIN" to 2,
        "PLUS" to 3,
        "USER" to 99
    )

    private val colorList: Map<Int, String> = mapOf(
        0 to "2444FC",
        1 to "334EFC",
        2 to "4258FD",
        3 to "5162FD",
        4 to "5F6DFE",
        5 to "6E77FE",
        6 to "7D81FF",
        7 to "8C8BFF"
    )

    private fun getRankTeamName(rank: String): String {
        val upperRank = rank.uppercase(Locale.getDefault())
        val priority = rankList.getOrDefault(upperRank, 99)
        return "${priority}_$upperRank"
    }

    // 팀 태그 동기화 (unregister 없이 prefix만 수정)
    private fun syncAllTeamTags(personalBoard: Scoreboard) {
        // 서버에 접속 중인 '모든' 플레이어를 순회하며
        for (other in Bukkit.getOnlinePlayers()) {
            val rank = getPlayerRank(other)
            val teamName = getRankTeamName(rank)

            // viewer의 개인 보드에 other 플레이어를 위한 팀이 있는지 확인
            val team = personalBoard.getTeam(teamName) ?: personalBoard.registerNewTeam(teamName)

            // 스타일 및 접두사 설정
            val uuid = other.uniqueId
            val playerStyle = applyStyle[uuid] ?: -1
            val playerStylePrefix = if (playerStyle == -1) "" else "${gc(symmetry[playerStyle])} "
            val rankPrefix = "${gc(getPlayerRankPrefix(other))} "

            val fullPrefix = "$playerStylePrefix$rankPrefix"

            // 성능 최적화: 프리픽스가 이미 같으면 패킷을 보내지 않음
            if (team.prefix != fullPrefix) {
                team.prefix = fullPrefix
                try {
                    // 컬러 설정 (§ 뒤의 한 글자를 가져와서 설정)
                    val colorChar = rankPrefix.substring(1, 2)
                    team.color = ChatColor.getByChar(colorChar) ?: ChatColor.WHITE
                } catch (e: Exception) {
                    team.color = ChatColor.WHITE
                }
            }

            // 팀에 플레이어(other) 추가
            if (!team.hasEntry(other.name)) {
                team.addEntry(other.name)
            }

            // 탭 리스트 이름 업데이트 (본인 포함 타인에게 보이는 이름)
            val listName = "$fullPrefix${other.name}"
            if (other.playerListName != listName) {
                other.setPlayerListName(listName)
            }
        }
    }

    // 점수 업데이트 (이전 점수 삭제 로직 포함)
    private fun Objective.updateLine(msg: String, score: Int) {
        val formattedMsg = gc(msg)
        this.getScore(formattedMsg).score = score
    }

    fun scoreboard(player: Player, count: Int) {
        val uuid = player.uniqueId

        // 1. 보드 재사용 로직
        val personalBoard = boards.getOrPut(uuid) {
            val newBoard = Bukkit.getScoreboardManager().newScoreboard
            player.scoreboard = newBoard
            newBoard
        }

        // 2. 사이드바 Objective 설정
        val sidebar = personalBoard.getObjective("sidebar")
            ?: personalBoard.registerNewObjective("sidebar", "dummy", "§l§oMETALISM")

        sidebar.displaySlot = DisplaySlot.SIDEBAR

        // 3. 타이틀 애니메이션 (count 기반)
        val title = StringBuilder()
        val rawTitle = "METALISM"
        for (i in rawTitle.indices) {
            title.append(rgb(colorList[(count + i) % 8]!!)).append("§l§o").append(rawTitle[i])
        }
        sidebar.displayName = title.toString()

        // 4. 데이터 로드
        val gold = PlayerDataCache.gold[uuid] ?: 0L
        val cash = PlayerDataCache.cash[uuid] ?: 0L
        val kill = PlayerDataCache.kill[uuid] ?: 0L
        val death = PlayerDataCache.death[uuid] ?: 0L
        val nextEventTime = nextEvent - (System.currentTimeMillis() / 1000)

        val playerStyle = applyStyle[uuid] ?: -1
        val playerStylePrefix = if (playerStyle == -1) "" else "${gc(symmetry[playerStyle])} "
        val rankPrefix = "${gc(getPlayerRankPrefix(player))} "

        personalBoard.objectives.forEach { it.unregister() }

        // 5. 사이드바 내용 업데이트
        sidebar.updateLine("&f&l플레이어: $playerStylePrefix$rankPrefix${player.name}", 99)
        sidebar.updateLine(player, "&1", 98) // 빈 줄 구분
        sidebar.updateLine(player, "&f&l골드: &6&l${gold.toFormat()} 골드", 97)
        sidebar.updateLine(player, "&f&l캐시: &b&l${cash.toFormat()} 캐시", 96)
        sidebar.updateLine(player, "&2", 95)
        sidebar.updateLine(player, "&3", 94)
        sidebar.updateLine(player, "&f&l처치 수: &a&l${kill.toFormat()}", 93)
        sidebar.updateLine(player, "&f&l사망 수: &c&l${death.toFormat()}", 92)
        sidebar.updateLine(player, "&4", 91)
        sidebar.updateLine(player, "&f&l추천: &c&lX", 90)
        sidebar.updateLine(player, "&5", 89)

        if (nextEvent != -1L) {
            sidebar.updateLine(player, "&8다음 난이도 선택까지 ${nextEventTime}초", 88)
        } else {
            sidebar.updateLine(player, "&8이벤트가 종료 되었습니다.", 87)
        }
        sidebar.updateLine(player, "&8metalism.kro.kr | ${player.ping} ms", 86)

        // 6. 탭 리스트 및 태그 동기화
        syncAllTeamTags(personalBoard)
    }

    // 플레이어 나갈 때 호출해서 메모리 정리
    fun cleanup(uuid: UUID) {
        boards.remove(uuid)
        lastLines.remove(uuid)
    }
}