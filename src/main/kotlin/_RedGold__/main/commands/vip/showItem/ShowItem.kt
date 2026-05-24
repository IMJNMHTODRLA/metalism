package _RedGold__.main.commands.vip.showItem

import _RedGold__.main.functions.Color.fail
import _RedGold__.main.functions.TimeTool.now
import _RedGold__.main.functions.smartBroadcast
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

@RequireCommandExecutor("showitem", PermissionEnum.VIP, aliases = ["sitem"])
@RequireTabExecutor
@RequireJavaPlugin
class ShowItem(private val plugin: JavaPlugin) : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false
        val uuid = player.uniqueId
        val cooldown = ShowItemValue.cooldownMap[uuid]?: 0L

        if (cooldown > now) {
            player.fail("&c${cooldown - now}초 후에 다시 시도할 수 있습니다.")
            return true
        }

        val item = player.inventory.itemInMainHand

        if (item.type == Material.AIR) {
            player.fail("&c들고 있는 아이템이 없습니다.")
            return true
        }

        plugin.server.smartBroadcast {
            text("&e&l[${player.name}님의 ${item.itemMeta.displayName}&e&l 아이템]") {
                item(item)
            }
        }

        ShowItemValue.cooldownMap[uuid] = now + 5
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ) = emptyList<String>()
}