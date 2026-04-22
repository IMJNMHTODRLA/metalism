package _RedGold__.main.commands.mvp.nick

import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.FastBoolean.falseRun
import _RedGold__.main.functions.FastBoolean.trueRun
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireLinkPacketListener
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

@RequireCommandExecutor("nick", PermissionEnum.MVP, "&c/<command> <변경할 닉네임(한글 사용 가능)>", ["닉변", "닉네임"])
@RequireTabExecutor
@RequireLinkPacketListener(NickPacketListener::class)
class Nick(private val listener: NickPacketListener) : CommandExecutor, TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.isEmpty()) return false

        val player = sender as? Player?: return false
        val uuid = player.uniqueId
        val cooldownMap = NickValue.cooldownMap
        val nickName = args[0]

        if ((cooldownMap[uuid]?: 0L) > now) {
            player.fail("&c${(cooldownMap[uuid]?: 0L) - now}초 뒤에 변경이 가능합니다.")
            return true
        }

        if (!NickConst.IS_PERFECT_NAME(nickName)) {
            player.fail("&ca~z, A~Z, 0~9, 가~힣, _, 공백, 특수문자만 사용한 3~20자(색코드를 포함 안한) 이내의 닉네임만 가능합니다.")
            player.sendMsg("&8&l(\\&를 사용하여 색코드 적용이 가능합니다. 예: \\&c는 빨간색)")
            player.sendMsg("&8&l(만일 \\&문자 자체를 사용하고 싶으면 \\\\\\&를 사용하면 됩니다.)")
            return false
        }

        listener.changeNick(player, nickName.gc())
            .trueRun {
                cooldownMap[uuid] = now + 1800
                player.good("&a&l닉네임 변경이 완료되었습니다. &f&l기존 스킨 및 닉네임으로 초기화를 원할 시 재접속을 하시길 바랍니다.")
            }
            .falseRun {
                player.fail("&c서버 내부 문제로 닉네임 변경에 실패했습니다.")
            }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return listOf("변경할 닉네임(한글 사용 가능)")
        return emptyList()
    }
}