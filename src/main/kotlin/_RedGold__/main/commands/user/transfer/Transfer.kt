package _RedGold__.main.commands.user.transfer

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.FastReplace.fill
import _RedGold__.main.functions.getTabPlayers
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.playerData.data
import _RedGold__.main.managers.playerData.variableManager.cosmeticManager.CosmeticEnum
import _RedGold__.main.managers.playerData.variableManager.cosmeticManager.STYLE_COSMETIC
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

@RequireCommandExecutor("transfer", PermissionEnum.USER, "&c/<command> <플레이어> <금액>", ["송금", "pay", "보내기"])
@RequireTabExecutor
class Transfer : CommandExecutor, TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.size < 3) return false

        val sendPlayer = sender as? Player?: return true
        val sendGold = args[1].toLongOrNull()?.also {
            if (it <= 0) {
                sendPlayer.sendMsg("&c0 골드 이하로는 보낼 수 없습니다.")
                return true
            }
            if (it > sendPlayer.data.gold) {
                sendPlayer.sendMsg("&c보유 골드가 부족합니다.")
                return true
            }
        }?: return false

        val receivePlayer = Bukkit.getPlayer(args[0])?: run {
            sendPlayer.sendMsg("&c해당 플레이어는 접속 중이 아닙니다.")
            return true
        }

        val sendStyle = sendPlayer.data.equipStyle.let {
            if (it.first) "${it.second} "
            else ""
        }
        val sendRank = PermissionEnum[sendPlayer].prefix
        val sendName = sendPlayer.name

        val receiveStyle = receivePlayer.data.equipStyle.let {
            if (it.first) "${it.second} "
            else ""
        }
        val receiveRank = PermissionEnum[receivePlayer].prefix
        val receiveName = receivePlayer.name

        sendPlayer.sendMsg(TransferConst.SEND_MESSAGE.fill(
            "style" to receiveStyle,
            "rank" to receiveRank,
            "name" to receiveName,
            "amount" to sendGold
        ))

        receivePlayer.sendMsg(TransferConst.RECEIVE_MESSAGE.fill(
            "style" to sendStyle,
            "rank" to sendRank,
            "name" to sendName,
            "amount" to sendGold
        ))

        sendPlayer.data.gold -= sendGold
        receivePlayer.data.gold += sendGold
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return getTabPlayers(args[0]) { it.name }
        if (args.size == 2) return listOf("<금액>")

        return emptyList()
    }
}