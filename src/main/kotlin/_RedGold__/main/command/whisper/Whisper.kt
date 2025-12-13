package _RedGold__.main.command.whisper

import _RedGold__.main.command.boost.sys.admin.Admin
import _RedGold__.main.command.boost.sys.goDiscord.GoDiscord
import _RedGold__.main.command.boost.sys.infoGui.InfoGui
import _RedGold__.main.command.boost.sys.selectGui.SelectGui
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Rank.getPlayerRankPrefix
import _RedGold__.main.load.RequireCommandExecutor
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireTabExecutor
import _RedGold__.main.sys.Chat.ChatApply.applyStyle
import _RedGold__.main.sys.Chat.ChatApply.symmetry
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import java.util.*
import java.util.stream.Collectors

@RequireCommandExecutor("whisper", "user", "&c/<command> <플레이어> <메시지>", ["w", "msg", "tell"])
@RequireTabExecutor
class Whisper : CommandExecutor, TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return true
        val name = player.name
        val uuid = player.uniqueId

        val playerStyle = applyStyle[uuid]?: -1
        val playerStylePrefix = if (playerStyle == -1) "" else "${gc(symmetry[playerStyle])} "
        val rankPrefix = "${gc(getPlayerRankPrefix(player))} "

        if (args.size < 2) return false

        val toPlayer = Bukkit.getPlayer(args[0])
        if (toPlayer == null) {
            player.sendMessage(gc("&c해당 플레이어는 접속 중이 아닙니다."))
            return true
        }
        val toName = toPlayer.name
        val toUuid = toPlayer.uniqueId

        val toPlayerStyle = applyStyle[toUuid]?: -1
        val toPlayerStylePrefix = if (toPlayerStyle == -1) "" else "${gc(symmetry[toPlayerStyle])} "
        val toRankPrefix = "${gc(getPlayerRankPrefix(toPlayer))} "

        val sendMessages = args.drop(1).joinToString(" ")

        player.sendMessage(gc("&b&lME &8-> $toPlayerStylePrefix$toRankPrefix$toName&f: ") + sendMessages)
        player.sendMessage(gc("$playerStylePrefix$rankPrefix$name &8-> &a&lME&f: ") + sendMessages)
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return Bukkit.getOnlinePlayers().stream()
                                .map { obj -> obj.name }
                                .filter { name -> name.lowercase(Locale.getDefault()).startsWith(args[0].lowercase(Locale.getDefault())) }
                                .collect(Collectors.toList())
        return emptyList()
    }
}