package _RedGold__.main.sys

import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.nextEvent
import _RedGold__.main.event.randomEffect.System.RandomEffectEvent.point
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Color.rgb
import _RedGold__.main.function.Data.getData
import _RedGold__.main.function.Rank.getPlayerRank
import _RedGold__.main.function.Rank.getPlayerRankPrefix
import _RedGold__.main.function.api.toFormat
import _RedGold__.main.sys.Chat.ChatApply.applyStyle
import _RedGold__.main.sys.Chat.ChatApply.symmetry
import _RedGold__.main.sys.RunScoreboard.PlayerDataCache
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
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

        val token = PlayerDataCache.token[uuid]?: 0L
        val advancedToken = PlayerDataCache.advancedToken[uuid]?: 0L

        val nextEventTime = nextEvent - (System.currentTimeMillis() / 1000)

        sidebar.s("&f&l플레이어: $playerStylePrefix$rankPrefix$playerName", 99)
        sidebar.s("", 98)
        sidebar.s("&f&l골드: &6&l${gold.toFormat()} 골드", 97)
        sidebar.s("&f&l캐시: &b&l${cash.toFormat()} 캐시", 96)
        sidebar.s(" ", 95)
        sidebar.s("&f&l토큰: &2&l${token.toFormat()} 토큰", 94)
        sidebar.s("&f&l고급 토큰: &a&l${advancedToken.toFormat()} 토큰", 93)
        sidebar.s("  ", 92)
        sidebar.s("&f&l처치 수: &a&l${kill.toFormat()}", 91)
        sidebar.s("&f&l사망 수: &c&l${death.toFormat()}", 90)
        sidebar.s("   ", 89)
        sidebar.s("&f&l추천: &c&lX", 88)
        sidebar.s("    ", 87)
        if (nextEvent != -1L) sidebar.s("&8다음 난이도 선택까지 ${nextEventTime}초", 86)
        else sidebar.s("&8이벤트가 종료 되었습니다.", 86)
        sidebar.s("&8metalism.kro.kr | ${player.ping} ms", 85)

        player.scoreboard = personalBoard
        syncAllTeamTags(player)
    }
}