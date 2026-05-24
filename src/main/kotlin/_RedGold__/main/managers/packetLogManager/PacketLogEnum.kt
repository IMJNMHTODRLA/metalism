package _RedGold__.main.managers.packetLogManager

enum class PacketSendEnum(val prefix: String) {
    CLIENT("Client -> Server"),
    SERVER("Server -> Client")
}