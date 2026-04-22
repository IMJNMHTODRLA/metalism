package _RedGold__.main.commands.mission

import _RedGold__.main.commands.mission.sys.achievementGui.AchievementGui
import _RedGold__.main.commands.mission.sys.dailyGui.DailyGui
import _RedGold__.main.commands.mission.sys.weeklyGui.WeeklyGui
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireTabExecutor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

@RequireCommandExecutor("mission", "user")
@RequireTabExecutor
@RequireJavaPlugin
class Mission(private val plugin: JavaPlugin) : CommandExecutor, TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player ?: return false

        if (args.isEmpty()) return true
        else if (args[0] == "daily") DailyGui(plugin).openGui(player)
        else if (args[0] == "weekly") WeeklyGui(plugin).openGui(player)
        else if (args[0] == "achievement") AchievementGui(plugin).openGui(player, 0)

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return listOf("daily", "weekly", "achievement")
        return emptyList()
    }
}