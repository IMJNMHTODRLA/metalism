package _RedGold__.main.command.rank

import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Rank.getRankPrefix
import _RedGold__.main.load.RequireCommandExecutor
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireListener
import _RedGold__.main.load.RequireTabExecutor
import net.luckperms.api.LuckPermsProvider
import net.luckperms.api.model.user.User
import net.luckperms.api.node.Node
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*
import java.util.stream.Collectors

@RequireCommandExecutor("rank", "owner")
@RequireTabExecutor
class Rank : CommandExecutor, TabExecutor {
    private fun permissionRemove(user: User, permission: String) {
        user.data().remove(Node.builder(permission).value(true).build())
    }

    private fun permissionAdd(user: User, permission: String) {
        user.data().add(Node.builder(permission).value(true).build())
    }

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (args.size < 2) return false
        if (args[1] !in listOf("user", "plus", "admin", "owner")) return false

        val player = sender as Player
        val offlineTarget = Bukkit.getOfflinePlayer(args[0])

        if (!offlineTarget.hasPlayedBefore() && !offlineTarget.isOnline) {
            player.sendMessage(gc("&c해당 플레이어는 존재하지 않습니다."))
            return true
        }

        val api = LuckPermsProvider.get()
        val user = api.userManager.loadUser(offlineTarget.uniqueId).join()

        permissionRemove(user, "Main.user")
        permissionRemove(user, "Main.plus")
        permissionRemove(user, "Main.admin")
        permissionRemove(user, "Main.owner")

        permissionAdd(user, "Main.${args[1]}")

        api.userManager.saveUser(user)
        player.sendMessage(gc(
            "랭크: ${args[1]}, 펄미션: Main.${args[1]}, prefix: ${getRankPrefix(args[1])}"
        ))

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) {
            return Bukkit.getOnlinePlayers().stream()
                .map { obj -> obj.name }
                .filter { name -> name.lowercase(Locale.getDefault()).startsWith(args[0].lowercase(Locale.getDefault())) }
                .collect(Collectors.toList())
        } else if (args.size == 2) {
            return listOf("user", "plus", "admin", "owner")
        }

        return emptyList()
    }
}