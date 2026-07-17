package _RedGold__.main.commands.user.boost

import _RedGold__.main.commands.user.boost.listeners.info.infoGui.InfoGui
import _RedGold__.main.commands.user.boost.listeners.selectGui.SelectGui
import _RedGold__.main.commands.user.boost.listeners.settings.settingsGui.SettingsGui
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireTabExecutor
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

@RequireCommandExecutor("boost", PermissionEnum.USER, aliases = ["후원"])
@RequireTabExecutor
@RequireJavaPlugin
class Boost(private val plugin: JavaPlugin) : CommandExecutor, TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false

        when {
            args.isEmpty() -> SelectGui().openGui(player)
            args[0] == "info" -> InfoGui().openGui(player)
            args[0] == "setting" -> SettingsGui().openGui(player)
            args[0] == "apply" -> { /*ApplyGui().openGui(player)*/ }
        } //TODO: 만들어야한다

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return listOf("info", "setting", "apply")
        return emptyList()
    }
}
