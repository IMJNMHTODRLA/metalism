package _RedGold__.main.command.nick

import _RedGold__.main.Main.Boost.monthlySubData
import _RedGold__.main.function.Color.fail
import _RedGold__.main.function.Color.good
import _RedGold__.main.function.Scheduler.task
import _RedGold__.main.function.Scheduler.taskAsync
import _RedGold__.main.load.RequireCommandExecutor
import _RedGold__.main.load.RequireJavaPlugin
import _RedGold__.main.load.RequireTabExecutor
import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.wrappers.*
import com.google.gson.JsonParser
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.net.URL
import java.util.*

@RequireCommandExecutor("nick", "plus", "&c/<command> <변경할 닉네임(한글 사용 가능)>", ["닉변", "닉네임"])
@RequireTabExecutor
@RequireJavaPlugin
class Nick(private val plugin: JavaPlugin) : CommandExecutor, TabExecutor {
    private val cooldownTime: MutableMap<UUID, Long> = mutableMapOf()

    private fun changeNick121(player: Player, nickName: String): Boolean {
        return try {
            val manager = ProtocolLibrary.getProtocolManager()
            val oldProfile = WrappedGameProfile.fromPlayer(player)

            val newProfile = WrappedGameProfile(
                player.uniqueId,
                nickName
            )

            oldProfile.properties["textures"].forEach {
                newProfile.properties.put("textures", it)
            }

            val removePacket = manager.createPacket(PacketType.Play.Server.PLAYER_INFO_REMOVE)
            removePacket.uuidLists.write(0, listOf(player.uniqueId))

            val addPacket = manager.createPacket(PacketType.Play.Server.PLAYER_INFO)
            addPacket.playerInfoActions.write(
                0,
                EnumSet.of(
                    EnumWrappers.PlayerInfoAction.ADD_PLAYER,
                    EnumWrappers.PlayerInfoAction.UPDATE_LISTED,
                    EnumWrappers.PlayerInfoAction.UPDATE_GAME_MODE,
                    EnumWrappers.PlayerInfoAction.UPDATE_LATENCY
                )
            )

            val infoData = PlayerInfoData(
                player.uniqueId,
                player.ping,
                true,
                EnumWrappers.NativeGameMode.fromBukkit(player.gameMode),
                newProfile,
                null
            )

            addPacket.playerInfoDataLists.write(0, listOf(infoData))

            for (online in Bukkit.getOnlinePlayers()) {
                manager.sendServerPacket(online, removePacket)
                manager.sendServerPacket(online, addPacket)

                if (online.world != player.world) continue

                plugin.task(1) {
                    online.hidePlayer(plugin, player)
                    plugin.task(1) {
                        online.showPlayer(plugin, player)
                    }
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun isPerfectName(nickName: String): Boolean {
        if (nickName.length !in 2..11) return false

        val regex = Regex("^[a-zA-Z0-9가-힣_ ~`!@#$%^&*()]+$")
        return regex.matches(nickName)
    }

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val player = sender as? Player ?: return false
        val uuid = player.uniqueId
        val now = System.currentTimeMillis() / 1000

        if (args.isEmpty()) return false

        if ((monthlySubData[uuid]?: 0L) <= now) {
            player.fail("&c이 명령어는 월정액 유저만 사용 가능합니다.")
            return true
        }

        if ((cooldownTime[uuid]?: 0L) > now) {
            player.fail("&c${(cooldownTime[uuid]?: 0L) - now}초 뒤에 변경이 가능합니다.")
            return true
        }

        val nickName = args[0]
        if (!isPerfectName(nickName)) {
            player.fail("&ca~z, A~Z, 0~9, 가~힣, _, 공백, 특수문자만 사용이 가능합니다.")
            return false
        }

        val changeCompleted = changeNick121(player, nickName)
        if (changeCompleted) {
            cooldownTime[uuid] = (System.currentTimeMillis() / 1000) + 300
            player.good("&a&l닉네임 변경이 완료되었습니다. &f&l기존 스킨 및 닉네임으로 초기화를 원할 시 재접속을 하시길 바랍니다.")
        } else {
            player.fail("&c서버 내부 문제로 닉네임 변경에 실패했습니다.")
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return listOf("변경할 닉네임(한글 사용 가능)")
        return emptyList()
    }
}