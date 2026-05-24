package _RedGold__.main.managers.packetLogManager

import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.event.PacketSendEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientChatMessage
import org.bukkit.entity.Player

class PacketLogListener : PacketListener {
    override fun onPacketReceive(event: PacketReceiveEvent) {
        val player = event.getPlayer<Player>()
        val type = event.packetType

        when(type) {
            PacketType.Play.Client.PLAYER_POSITION -> {

            }
        }

        println("[Client -> Server] ${player.name}: ${type.name}")

        // 특정 패킷 데이터 읽기 (예: 채팅 패킷)
        if (type == PacketType.Play.Client.CHAT_MESSAGE) {
            val wrapper = WrapperPlayClientChatMessage(event)
            println("채팅 내용: ${wrapper.message}")
        }
    }

    // 서버 -> 클라이언트 (보내는 패킷)
    override fun onPacketSend(event: PacketSendEvent) {
        val player = event.getPlayer<Player>()
        val type = event.packetType

        // 예: 서버가 보내는 위치 업데이트 패킷 확인
        if (type == PacketType.Play.Server.ENTITY_RELATIVE_MOVE) {
            // 상세 로직 처리
        }
    }
}

//TODO: 나중에 시간 좀 남으면 만들어야지
//TODO: 매 주 마다 패킷 로그 분석해서 밴 웨이브 실행
//TODO: plugin_folder/packet_log/yyyy-MM-dd/HH:mm:ss/client.txt
//TODO: plugin_folder/packet_log/yyyy-MM-dd/HH:mm:ss/server.txt
