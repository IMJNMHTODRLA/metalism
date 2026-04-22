package _RedGold__.main.functions

import _RedGold__.main.functions.Color.sendMsg
import _RedGold__.main.loads.SetSlowInit
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.PluginCommand
import org.bukkit.command.SimpleCommandMap
import org.bukkit.entity.Player

inline fun <reified T : CommandExecutor> Player.onCommand(vararg args: String) {
    val commandName = CommandCache.getName(T::class.java)

    if (!commandName.isNull) {
        this.performCommand("$commandName ${args.joinToString(" ")}".trim())
    } else {
        this.sendMsg("&c오류: ${T::class.simpleName}으로 등록된 명령어를 찾을 수 없습니다.")
    }
}

object CommandCache {
    private val classToNameMap = mutableMapOf<Class<out CommandExecutor>, String>()

    @SetSlowInit
    fun initialize() {
        classToNameMap.clear()
        try {
            val server = Bukkit.getServer()
            val commandMapField = server.javaClass.getDeclaredField("commandMap").apply { isAccessible = true }
            val commandMap = commandMapField.get(server) as SimpleCommandMap

            val knownCommandsField = SimpleCommandMap::class.java.getDeclaredField("knownCommands").apply { isAccessible = true }

            @Suppress("UNCHECKED_CAST")
            val knownCommands = knownCommandsField.get(commandMap) as Map<String, Command>

            knownCommands.values.forEach { cmd ->
                if (cmd is PluginCommand) {
                    val executor = cmd.executor

                    if (!classToNameMap.containsKey(executor::class.java)) {
                        classToNameMap[executor::class.java] = cmd.name
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getName(clazz: Class<out CommandExecutor>): String? = classToNameMap[clazz]
}