package _RedGold__.main.commands.vip.enderchest

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.functions.Gui.sendSound
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

@RequireCommandExecutor("enderchest", PermissionEnum.VIP, "&c/<command>", ["ec", "엔더상자", "엔상"])
@RequireTabExecutor
class EnderChest : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false
        player.openInventory(player.enderChest)

        player.sendMsg("&d엔더상자를 열었습니다.")
        player.sendSound(Sound.BLOCK_ENDER_CHEST_OPEN)
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        return emptyList()
    }
}