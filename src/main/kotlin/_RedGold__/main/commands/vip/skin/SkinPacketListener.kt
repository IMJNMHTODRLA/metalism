package _RedGold__.main.commands.vip.skin

import _RedGold__.main.loads.RequirePacketListener
import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.protocol.player.GameMode
import com.github.retrooper.packetevents.protocol.player.TextureProperty
import com.github.retrooper.packetevents.protocol.world.Location
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoRemove
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnPlayer
import com.google.gson.JsonParser
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

@RequirePacketListener
class SkinPacketListener : PacketListener {
    fun getSkinData(targetName: String): Pair<String, String>? {
        return try {
            val nickUrl = SkinConst.SET_NICK_URL(targetName)
            val nickJson = JsonParser.parseString(nickUrl.readText()).asJsonObject
            val uuid = nickJson["id"].asString

            val sessionUrl = SkinConst.SET_UUID_URL(uuid)
            val sessionJson = JsonParser.parseString(sessionUrl.readText()).asJsonObject
            val properties = sessionJson["properties"].asJsonArray

            properties.forEach { element ->
                val property = element.asJsonObject
                if (property["name"].asString != "textures") return@forEach

                val value = property["value"].asString
                val signature = property["signature"].asString
                return Pair(value, signature)
            }
            null
        } catch (e: Exception) {
            null
        }
    }

    fun changeSkin(player: Player, value: String, signature: String): Boolean {
        return try {
            val user = PacketEvents.getAPI().playerManager.getUser(player)
            val uuid = player.uniqueId
            val location = player.location

            val destroyPacket = WrapperPlayServerDestroyEntities(player.entityId)
            val spawnPacket = WrapperPlayServerSpawnPlayer(
                player.entityId,
                uuid,
                Location(
                    location.x, location.y, location.z,
                    location.yaw, location.pitch
                )
            )

            val textureProperty = TextureProperty("textures", value, signature)
            val removePacket = WrapperPlayServerPlayerInfoRemove(uuid)

            val playerInfo = WrapperPlayServerPlayerInfoUpdate.PlayerInfo(
                user.profile, true,
                player.ping, GameMode.getById(player.gameMode.value),
                null, null
            )

            playerInfo.gameProfile.textureProperties.clear()
            playerInfo.gameProfile.textureProperties.add(textureProperty)

            val updatePacket = WrapperPlayServerPlayerInfoUpdate(
                EnumSet.of(
                    WrapperPlayServerPlayerInfoUpdate.Action.ADD_PLAYER,
                    WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_LISTED,
                    WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_LATENCY,
                    WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_GAME_MODE
                ),
                playerInfo
            )

            Bukkit.getOnlinePlayers().forEach { online ->
                val onlineUser = PacketEvents.getAPI().playerManager.getUser(online)?: return@forEach

                onlineUser.sendPacket(removePacket)
                onlineUser.sendPacket(updatePacket)

                if (online.world != player.world || online == player) return@forEach

                onlineUser.sendPacket(destroyPacket)
                onlineUser.sendPacket(spawnPacket)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}