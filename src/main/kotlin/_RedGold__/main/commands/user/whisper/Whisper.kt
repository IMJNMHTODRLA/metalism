package _RedGold__.main.commands.user.whisper

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.FastReplace.fill
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.playerData.data
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

@RequireCommandExecutor("whisper", PermissionEnum.USER, "&c/<command> <플레이어> <메시지>", ["w", "msg", "tell"])
@RequireTabExecutor
class Whisper : CommandExecutor, TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.size < 2) return false
        val sendMessages = args.drop(1).joinToString(" ")

        val sendPlayer = sender as? Player?: return true
        val receivePlayer = Bukkit.getPlayer(args[0])?: run {
            sendPlayer.sendMsg("&c해당 플레이어는 접속 중이 아닙니다.")
            return true
        }

        val (_, sendStyle) = receivePlayer.data.equipStyle
        val sendRank = PermissionEnum[sendPlayer].prefix
        val sendName = sendPlayer.name

        val (_, receiveStyle) = receivePlayer.data.equipStyle
        val receiveRank = PermissionEnum[receivePlayer].prefix
        val receiveName = receivePlayer.name

        sendPlayer.sendMsg(WhisperConst.SEND_MESSAGE.fill(
            "style" to receiveStyle,
            "rank" to receiveRank,
            "name" to receiveName,
            "message" to sendMessages
        ))

        receivePlayer.sendMsg(WhisperConst.RECEIVE_MESSAGE.fill(
            "style" to sendStyle,
            "rank" to sendRank,
            "name" to sendName,
            "message" to sendMessages
        ))

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return Bukkit.getOnlinePlayers()
            .map { it.name }
            .filter { it.startsWith(args[0], true) }
        return emptyList()
    }
}