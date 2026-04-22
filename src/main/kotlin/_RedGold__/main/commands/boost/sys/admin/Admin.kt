package _RedGold__.main.commands.boost.sys.admin

import _RedGold__.main.function.Data.getDataUuid
import _RedGold__.main.function.Data.saveDataUuid
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

class Admin(private val plugin: JavaPlugin) {
    fun addBoost(uuid: UUID, amount: Long) {
        var boostAmount = getDataUuid(plugin, uuid, "boost").toLong()

        boostAmount += amount
        saveDataUuid(plugin, uuid, "boost", boostAmount)
    }

    fun removeBoost(uuid: UUID, amount: Long) {
        var boostAmount = getDataUuid(plugin, uuid, "boost").toLong()

        boostAmount -= amount
        saveDataUuid(plugin, uuid, "boost", boostAmount)
    }

    fun setBoost(uuid: UUID, amount: Long) {
        saveDataUuid(plugin, uuid, "boost", amount)
    }
}