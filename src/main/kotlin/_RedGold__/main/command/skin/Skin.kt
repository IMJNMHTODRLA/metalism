package _RedGold__.main.command.skin

import _RedGold__.main.Main.Boost.monthlySubData
import _RedGold__.main.function.Color.fail
import _RedGold__.main.function.Color.gc
import _RedGold__.main.function.Color.good
import _RedGold__.main.function.Scheduler
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
import java.util.stream.Collectors

@RequireCommandExecutor("skin", "plus", "&c/<command> <변경할 스킨의 닉네임>", ["스킨"])
@RequireTabExecutor
@RequireJavaPlugin
class Skin(private val plugin: JavaPlugin) : CommandExecutor, TabExecutor {
    private val cooldownTime: MutableMap<UUID, Long> = mutableMapOf()

    private fun fetchSkinData(targetName: String): Pair<String, String>? {
        return try {
            val uuidUrl = URL("https://api.mojang.com/users/profiles/minecraft/$targetName")
            val uuidJson = JsonParser.parseString(Scanner(uuidUrl.openStream()).useDelimiter("\\A").next()).asJsonObject
            val uuid = uuidJson.get("id").asString

            val sessionUrl = URL("https://sessionserver.mojang.com/session/minecraft/profile/$uuid?unsigned=false")
            val sessionJson = JsonParser.parseString(Scanner(sessionUrl.openStream()).useDelimiter("\\A").next()).asJsonObject
            val properties = sessionJson.getAsJsonArray("properties")

            for (element in properties) {
                val property = element.asJsonObject
                if (property.get("name").asString == "textures") {
                    val value = property.get("value").asString
                    val signature = property.get("signature").asString
                    return Pair(value, signature)
                }
            }
            null
        } catch (e: Exception) {
            null
        }
    }

    private fun changeSkin121(player: Player, value: String, signature: String): Boolean {
        return try {
            val manager = ProtocolLibrary.getProtocolManager()
            val gameProfile = WrappedGameProfile.fromPlayer(player)

            gameProfile.properties.removeAll("textures")
            gameProfile.properties.put("textures", WrappedSignedProperty("textures", value, signature))

            val removePacket = manager.createPacket(PacketType.Play.Server.PLAYER_INFO_REMOVE)
            removePacket.uuidLists.write(0, listOf(player.uniqueId))

            val updatePacket = manager.createPacket(PacketType.Play.Server.PLAYER_INFO)
            updatePacket.playerInfoActions.write(0, EnumSet.of(
                EnumWrappers.PlayerInfoAction.ADD_PLAYER,
                EnumWrappers.PlayerInfoAction.UPDATE_LISTED,
                EnumWrappers.PlayerInfoAction.UPDATE_LATENCY,
                EnumWrappers.PlayerInfoAction.UPDATE_GAME_MODE
            ))

            val infoData = PlayerInfoData(
                player.uniqueId,
                player.ping,
                true,
                EnumWrappers.NativeGameMode.fromBukkit(player.gameMode),
                gameProfile,
                null
            )
            updatePacket.playerInfoDataLists.write(0, listOf(infoData))

            for (online in Bukkit.getOnlinePlayers()) {
                manager.sendServerPacket(online, removePacket)
                manager.sendServerPacket(online, updatePacket)

                if (online.world != player.world) continue

                plugin.task(1) {
                    online.hidePlayer(plugin, player)
                    plugin.task(1) { online.showPlayer(plugin, player) }
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
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

        plugin.taskAsync {
            val skinData = fetchSkinData(args[0])
            if (skinData == null) {
                plugin.task { player.fail("&c스킨 데이터를 가져오지 못했습니다.") }
                return@taskAsync
            }

            plugin.task {
                val changeCompleted = changeSkin121(player, skinData.first, skinData.second)

                if (changeCompleted) {
                    cooldownTime[uuid] = (System.currentTimeMillis() / 1000) + 600
                    player.good("&a&l스킨 변경이 완료되었습니다. &f&l기존 스킨 및 닉네임으로 초기화를 원할 시 재접속을 하시길 바랍니다.")
                } else player.fail("&c서버 내부 문제로 스킨 변경에 실패했습니다.")
            }
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        if (args.size == 1) return listOf("변경할 스킨의 닉네임")
        return emptyList()
    }
}