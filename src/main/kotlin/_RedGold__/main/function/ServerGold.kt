package _RedGold__.main.function

import _RedGold__.main.function.api.WriteSave
import _RedGold__.main.function.api.readFileContents
import org.bukkit.plugin.java.JavaPlugin

object ServerGold {
    fun addHoldGold(plugin: JavaPlugin, value: Long) {
        WriteSave(plugin, "server_gold", "hold.data", "${getHoldGold(plugin).toLong() + value}")
    }

    fun getHoldGold(plugin: JavaPlugin): String {
        return readFileContents(plugin, "server_gold", "hold.data")
    }

    fun addMakeGold(plugin: JavaPlugin, value: Long) {
        WriteSave(plugin, "server_gold", "make.data", "${getMakeGold(plugin).toLong() + value}")
    }

    fun getMakeGold(plugin: JavaPlugin): String {
        return readFileContents(plugin, "server_gold", "make.data")
    }
}