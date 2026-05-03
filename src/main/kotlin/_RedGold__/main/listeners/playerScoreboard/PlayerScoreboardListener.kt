package _RedGold__.main.listeners.playerScoreboard

import _RedGold__.main.functions.Color.rgb
import _RedGold__.main.functions.EasyScoreBoard.score
import _RedGold__.main.functions.FastNumber.seconds
import _RedGold__.main.functions.FastNumber.ticks
import _RedGold__.main.functions.FastReplace.fill
import _RedGold__.main.functions.NumberFormat.toFormat
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.listeners.GlobalConst
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.playerData.data
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Scoreboard

@RequireJavaPlugin
class PlayerScoreboardListener(plugin: JavaPlugin) {
    init {
        plugin.task(0, 5.ticks) {
            val times = PlayerScoreboardValue.times

            for (player in Bukkit.getOnlinePlayers()) {
                scoreboard(player, times)
            }
            if (times == 0) PlayerScoreboardValue.times = 7 else PlayerScoreboardValue.times--
        }

        plugin.task(0, 8.seconds) {
            for (player in Bukkit.getOnlinePlayers()) {
                val board = PlayerScoreboardValue.boards[player.uniqueId]?: continue
                syncAllTeamTags(board)
            }
        }
    }

    private fun syncAllTeamTags(personalBoard: Scoreboard) {
        Bukkit.getOnlinePlayers().forEach { other ->
            val name = other.name
            val rank = PermissionEnum[other]
            val rankPrefix = rank.prefix
            val teamName = "${rank.priority}_${rank.node}"

            val (_, style) = other.data.equipStyle

            val team = personalBoard.getTeam(teamName)?: personalBoard.registerNewTeam(teamName)
            val fullPrefix = PlayerScoreboardConst.PLAYER_TAB_PREFIX.fill(
                "style" to style,
                "rank" to rankPrefix,
                "name" to name
            )

            if (team.prefix != fullPrefix) {
                val colorChar = rankPrefix.substring(1, 2)
                team.color = ChatColor.getByChar(colorChar)?: ChatColor.WHITE
                team.prefix = fullPrefix.replace(" $name", "")
            }

            if (!team.hasEntry(name)) team.addEntry(name)
            if (other.playerListName != fullPrefix) {
                other.setPlayerListName(fullPrefix)
            }
        }
    }

    private fun scoreboard(player: Player, times: Int) {
        val uuid = player.uniqueId
        val boards = PlayerScoreboardValue.boards
        val colorList = GlobalConst.COLOR_LIST
        val max = colorList.size
        val rank = PermissionEnum[player]
        val sidebarMsg = PlayerScoreboardConst.SIDEBAR_MSG

        val (_, style) = player.data.equipStyle

        val personalBoard = boards.getOrPut(uuid) {
            val newBoard = Bukkit.getScoreboardManager().newScoreboard
            player.scoreboard = newBoard
            newBoard
        }

        val sidebar = personalBoard.getObjective("sidebar")?: personalBoard.registerNewObjective("sidebar", "dummy", "")
        sidebar.displaySlot = DisplaySlot.SIDEBAR

        sidebar.displayName = """
            ${colorList[(times + 0) % max].rgb()}§l§oM
            ${colorList[(times + 1) % max].rgb()}§l§oE
            ${colorList[(times + 2) % max].rgb()}§l§oT
            ${colorList[(times + 3) % max].rgb()}§l§oA
            ${colorList[(times + 4) % max].rgb()}§l§oL
            ${colorList[(times + 5) % max].rgb()}§l§oI
            ${colorList[(times + 6) % max].rgb()}§l§oS
            ${colorList[(times + 7) % max].rgb()}§l§oM
        """.trimIndent().replace("\n", "")

        val fullPrefix = PlayerScoreboardConst.PLAYER_TAB_PREFIX.fill(
            "style" to style,
            "rank" to rank.prefix,
            "name" to player.name
        )

        personalBoard.entries.forEach { personalBoard.resetScores(it) }

        sidebar.score[99] = sidebarMsg[0].fill("fullPrefix" to fullPrefix)
        sidebar.score[98] = sidebarMsg[1]
        sidebar.score[97] = sidebarMsg[2].fill("gold" to player.data.gold.toFormat())
        sidebar.score[96] = sidebarMsg[3].fill("crystal" to player.data.crystal.toFormat())
        sidebar.score[95] = sidebarMsg[4].fill("ruby" to player.data.ruby.toFormat())
        sidebar.score[94] = sidebarMsg[5]
        sidebar.score[93] = sidebarMsg[6]
        sidebar.score[92] = sidebarMsg[7].fill("kill" to player.data.combatData.kill.toFormat())
        sidebar.score[91] = sidebarMsg[8].fill("death" to player.data.combatData.death.toFormat())
        sidebar.score[90] = sidebarMsg[9]
        sidebar.score[89] = sidebarMsg[10]
        sidebar.score[88] = sidebarMsg[11]
        sidebar.score[87] = sidebarMsg[12].fill("ping" to player.ping)
    }
}