package _RedGold__.main.commands.mvp.showEc

import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.functions.smartBroadcast
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.viewEcManager.viewEcNoiseMap
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import kotlin.random.Random

@RequireCommandExecutor("showec", PermissionEnum.MVP, aliases = ["sec"])
@RequireTabExecutor
@RequireJavaPlugin
class ShowEc(private val plugin: JavaPlugin) : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false
        val uuid = player.uniqueId
        val cooldown = ShowEcValue.cooldownMap[uuid]?: 0L

        if (cooldown > now) {
            player.fail("&c${cooldown - now}초 후에 다시 시도할 수 있습니다.")
            return true
        }

        val noise = Random.nextInt()

        plugin.server.smartBroadcast {
            text("&e&l[${player.name}님의 엔더 상자]") {
                command("viewec $uuid $noise")
            }
        }

        ShowEcValue.cooldownMap[uuid] = now + 20
        viewEcNoiseMap[uuid] = noise
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ) = emptyList<String>()
}