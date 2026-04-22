package _RedGold__.main.managers

import _RedGold__.main.managers.database.initDatabase
import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.plugin.java.JavaPlugin

class InitManager(private val plugin: JavaPlugin) {
    fun init() {
        initDatabase(plugin)
        PermissionEnum.entries.forEach {
            it.register(plugin)
        }
    }
}