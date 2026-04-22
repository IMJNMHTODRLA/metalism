package _RedGold__.main.commands.vip.skin

import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.Color.good
import _RedGold__.main.functions.FastBoolean.falseRun
import _RedGold__.main.functions.FastBoolean.trueRun
import _RedGold__.main.functions.Scheduler.task
import _RedGold__.main.functions.Scheduler.taskAsync
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireLinkPacketListener
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

@RequireCommandExecutor("skin", PermissionEnum.VIP, "&c/<command> <변경할 스킨의 닉네임>", ["스킨"])
@RequireTabExecutor
@RequireJavaPlugin
@RequireLinkPacketListener(SkinPacketListener::class)
class Skin(
    private val plugin: JavaPlugin,
    private val listener: SkinPacketListener
) : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.isEmpty()) return false

        val player = sender as? Player?: return false
        val uuid = player.uniqueId
        val cooldownMap = SkinValue.cooldownMap

        if ((cooldownMap[uuid]?: 0L) > now) {
            player.fail("&c${(cooldownMap[uuid]?: 0L) - now}초 뒤에 변경이 가능합니다.")
            return true
        }

        plugin.taskAsync {
            val (value, signature) = listener.getSkinData(args[0])?: run {
                plugin.task { player.fail("&c스킨 데이터를 가져오지 못했습니다.") }
                return@taskAsync
            }

            plugin.task {
                listener.changeSkin(player, value, signature)
                    .trueRun {
                        cooldownMap[uuid] = now + 1800
                        player.good("&a&l스킨 변경이 완료되었습니다. &f&l기존 스킨 및 닉네임으로 초기화를 원할 시 재접속을 하시길 바랍니다.")
                    }
                    .falseRun {
                        player.fail("&c서버 내부 문제로 스킨 변경에 실패했습니다.")
                    }
            }
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return listOf("변경할 스킨의 닉네임")
        return emptyList()
    }
}