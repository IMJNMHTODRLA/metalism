package _RedGold__.main.commands.admin.ban

import _RedGold__.main.functions.Color.gc
import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.NumberFormat.toUUIDOrNull
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.functions.task
import _RedGold__.main.functions.taskAsync
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.banManager.BanData
import _RedGold__.main.managers.banManager.BanEnum
import _RedGold__.main.managers.banManager.removeBan
import _RedGold__.main.managers.banManager.saveBan
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import java.util.*
import java.util.stream.Collectors

@RequireCommandExecutor("ban", PermissionEnum.ADMIN, "&cusage: /<command> [uuid] [temp|perm(only ban)] [reason(only ban)]", ["unban"])
@RequireTabExecutor
class Ban : CommandExecutor, TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val uuid = args.getOrNull(0).toUUIDOrNull()?: run {
            sender.sendMsg("&c&lUUID 변환 실패")
            return true
        }

        if (label == "unban") {
            if (args.isEmpty()) return false

            sender.sendMsg("&8&l밴 정보 삭제중...")
            taskAsync {
                removeBan(uuid)
                task {
                    sender.sendMsg("&a&l밴 정보 삭제 완료!")
                }
            }

            return true
        }

        if (label == "ban") {
            if (args.size < 3) return false

            val banData = BanData(
                uuid,
                when(args[1]) {
                    "perm" -> BanEnum.PERM_BAN
                    "temp" -> BanEnum.PERM_BAN

                    else -> return false
                },
                args[2],
                now + BanConst.BAN_EXP,
                now
            )

            sender.sendMsg("&8&l밴 정보 저장중...")
            taskAsync {
                saveBan(banData)
                task {
                    sender.sendMsg("&a&l밴 정보 저장 완료!")

                    Bukkit.getPlayer(uuid)?.kickPlayer("""
                        &c&l당신은 규칙 위반으로 인해 정지 되었습니다.
                        &c&l자세한 내용은 재접속을 하여 확인해주세요.
                    """.trimIndent().gc())
                }
            }

            return true
        }

        return false
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return Bukkit.getOnlinePlayers().stream()
            .filter { player -> player.name.lowercase(Locale.getDefault()).startsWith(args[0].lowercase(Locale.getDefault())) }
            .map { player -> player.uniqueId.toString() }
            .collect(Collectors.toList())

        if (args.size == 2 && label == "ban") return listOf("temp", "perm")
        if (args.size == 3 && label == "ban") return listOf("예시: 게임 플레이 규칙 1조 2항 위반")

        return emptyList()
    }
}