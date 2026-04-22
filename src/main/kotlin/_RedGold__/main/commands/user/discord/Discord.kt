package _RedGold__.main.commands.user.discord

import _RedGold__.main.functions.smartMessage
import _RedGold__.main.loads.RequireCommandExecutor
import _RedGold__.main.loads.RequireJavaPlugin
import _RedGold__.main.managers.playerData.DISCORD_INVITE
import _RedGold__.main.managers.playerData.PermissionEnum
import _RedGold__.main.managers.playerData.YOUTUBE_CHANNEL
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

@RequireJavaPlugin
@RequireCommandExecutor("discord", PermissionEnum.USER, "", ["디스코드", "커뮤니티"])
class Discord : TabExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player?: return false

        player.smartMessage {
            text("&c&l[유튜브 채널]\n") {
                hover("&7클릭하여 유튜브 채널에 방문")
                url(YOUTUBE_CHANNEL)
            }
            text("&b&l[디스코드 서버]") {
                hover("&7클릭하여 디스코드 서버에 접속")
                url(DISCORD_INVITE)
            }
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ) = emptyList<String>()
}