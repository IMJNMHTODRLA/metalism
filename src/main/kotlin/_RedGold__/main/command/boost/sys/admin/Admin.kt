package _RedGold__.main.command.boost.sys.admin

import _RedGold__.main.function.Data.getDataUuid
import _RedGold__.main.function.Data.saveDataUuid
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.model.user.User
import net.luckperms.api.node.Node
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

class Admin(private val plugin: JavaPlugin) {
    private fun permissionRemove(user: User, permission: String) {
        user.data().remove(Node.builder(permission).value(true).build())
    }

    private fun permissionAdd(user: User, permission: String) {
        user.data().add(Node.builder(permission).value(true).build())
    }

    fun addBoost(uuid: UUID, amount: Long) {
        var boostAmount = getDataUuid(plugin, uuid, "boost").toLong()

        boostAmount += amount

        val api = LuckPermsProvider.get()
        val user = api.userManager.loadUser(uuid).join()
        if (boostAmount >= 10000 && user.cachedData.permissionData.checkPermission("Main.user").asBoolean()) {
            permissionAdd(user, "Main.plus")
            api.userManager.saveUser(user)
        }

        saveDataUuid(plugin, uuid, "boost", boostAmount)
    }

    fun removeBoost(uuid: UUID, amount: Long) {
        var boostAmount = getDataUuid(plugin, uuid, "boost").toLong()

        boostAmount -= amount

        val api = LuckPermsProvider.get()
        val user = api.userManager.loadUser(uuid).join()
        if (boostAmount <= 10000 && user.cachedData.permissionData.checkPermission("Main.plus").asBoolean()) {
            permissionRemove(user, "Main.plus")
            api.userManager.saveUser(user)
        }

        saveDataUuid(plugin, uuid, "boost", boostAmount)
    }

    fun setBoost(uuid: UUID, amount: Long) {
        saveDataUuid(plugin, uuid, "boost", amount)
    }
}