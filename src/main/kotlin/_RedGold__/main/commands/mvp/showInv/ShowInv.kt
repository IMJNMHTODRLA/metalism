package _RedGold__.main.commands.mvp.showInv

import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.functions.smartBroadcast
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.viewInvManager.viewInvNoiseMap
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import kotlin.random.Random

@RequireCommandExecutor("showinv", PermissionEnum.MVP, aliases = ["sinv"])
@RequireTabExecutor
@RequireJavaPlugin
class ShowInv(private val plugin: JavaPlugin) : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false
        val uuid = player.uniqueId
        val cooldown = ShowInvValue.cooldownMap[uuid]?: 0L

        if (cooldown > now) {
            player.fail("&c${cooldown - now}초 후에 다시 시도할 수 있습니다.")
            return true
        }

        val noise = Random.nextInt()

        plugin.server.smartBroadcast {
            text("&e&l[${player.name}님의 인벤토리]") {
                command("viewinv $uuid $noise")
            }
        }

        ShowInvValue.cooldownMap[uuid] = now + 20
        viewInvNoiseMap[uuid] = noise
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ) = emptyList<String>()
}