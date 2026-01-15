package _RedGold__.main.command.enderchest

import _RedGold__.main.Main.Boost.monthlySubData
import _RedGold__.main.function.Color.fail
import _RedGold__.main.function.Color.gc
import _RedGold__.main.load.RequireCommandExecutor
import _RedGold__.main.load.RequireTabExecutor
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import java.util.*
import java.util.stream.Collectors

@RequireCommandExecutor("enderchest", "plus", "&c/<command>", ["ec", "엔더상자", "엔상"])
@RequireTabExecutor
class EnderChest : CommandExecutor, TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false
        if ((monthlySubData[player.uniqueId]?: 0L) <= System.currentTimeMillis() / 1000) {
            player.fail("&c이 명령어는 월정액 유저만 사용 가능합니다.")
            return true
        }

        player.openInventory(player.enderChest)

        player.sendMessage(gc("&d엔더상자를 열었습니다."))
        player.playSound(player.location, Sound.BLOCK_ENDER_CHEST_OPEN, 1.0f, 1.0f)
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