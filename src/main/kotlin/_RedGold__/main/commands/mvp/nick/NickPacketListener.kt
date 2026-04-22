package _RedGold__.main.commands.mvp.nick

import _RedGold__.main.loads.RequirePacketListener
import com.github.retrooper.packetevents.PacketEvents
import com.github.retrooper.packetevents.protocol.player.GameMode
import com.github.retrooper.packetevents.protocol.player.UserProfile
import com.github.retrooper.packetevents.protocol.world.Location
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoRemove
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnPlayer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

@RequirePacketListener
class NickPacketListener {
    fun changeNick(player: Player, name: String): Boolean {
        return try {
            val user = PacketEvents.getAPI().playerManager.getUser(player)?: return false
            val uuid = player.uniqueId
            val location = player.location

            val spawnPacket = WrapperPlayServerSpawnPlayer(
                player.entityId,
                uuid,
                Location(
                    location.x, location.y, location.z,
                    location.yaw, location.pitch
                )
            )

            val newProfile = UserProfile(uuid, name)
            user.profile.textureProperties.forEach {
                newProfile.textureProperties.add(it)
            }

            val removePacket = WrapperPlayServerPlayerInfoRemove(uuid)
            val destroyPacket = WrapperPlayServerDestroyEntities(player.entityId)

            val infoUpdatePacket = WrapperPlayServerPlayerInfoUpdate(
                EnumSet.of(
                    WrapperPlayServerPlayerInfoUpdate.Action.ADD_PLAYER,
                    WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_LISTED,
                    WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_GAME_MODE,
                    WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_LATENCY
                ),
                WrapperPlayServerPlayerInfoUpdate.PlayerInfo(
                    newProfile, true,
                    player.ping, GameMode.getById(player.gameMode.value),
                    null, null
                )
            )

            Bukkit.getOnlinePlayers().forEach { online ->
                val onlineUser = PacketEvents.getAPI().playerManager.getUser(online)?: return@forEach

                onlineUser.sendPacket(removePacket)
                onlineUser.sendPacket(infoUpdatePacket)

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