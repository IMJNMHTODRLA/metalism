package _RedGold__.main.commands.cash

import _RedGold__.main.function.Data.getDataUuid
import _RedGold__.main.function.Data.saveDataUuid
import _RedGold__.main.function.api.toUuid
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.loads.RequireTabExecutor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import java.util.stream.Collectors

@RequireCommandExecutor("cash", "admin")
@RequireTabExecutor
@RequireJavaPlugin
class Cash(private val plugin: JavaPlugin) : CommandExecutor, TabExecutor {
    private fun UUID.cashSet(amount: Long) {
        saveDataUuid(plugin, this, "cash", amount)
    }

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player
        if (args.size != 3) return false

        val uuid = args[0].toUuid()
        val amount = args[2].toLongOrNull()?: 0L

        if (player == null) {
            if (args[1] == "add") uuid.cashSet(getDataUuid(plugin, uuid, "cash").toLong() + amount)
            if (args[1] == "remove") uuid.cashSet(getDataUuid(plugin, uuid, "cash").toLong() - amount)
            if (args[1] == "set") uuid.cashSet(amount)
            return true
        }

        if (args[1] == "add") uuid.cashSet(getDataUuid(plugin, uuid, "cash").toLong() + amount)
        if (args[1] == "remove") uuid.cashSet(getDataUuid(plugin, uuid, "cash").toLong() - amount)
        if (args[1] == "set") uuid.cashSet(amount)

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        return when (args.size) {
            1 -> {
                Bukkit.getOnlinePlayers().stream()
                    .filter { player -> player.name.lowercase(Locale.getDefault()).startsWith(args[0].lowercase(Locale.getDefault())) }
                    .map { player -> player.uniqueId.toString() }
                    .collect(Collectors.toList())
            }

            2 -> listOf("add", "remove", "set")
            3 -> listOf("캐시를 입력하세요")
            else -> emptyList()
        }
    }
}