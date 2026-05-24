package _RedGold__.main.managers.packetLogManager

import _RedGold__.main.functions.TimeTool.nowMs
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType.Play.Client
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerPosition
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientVehicleMove
import org.bukkit.entity.Player
import sun.misc.Unsafe

fun writeReceivePacketLog(event: PacketReceiveEvent, player: Player): Boolean {
    val uuid = player.uniqueId
    val name = player.name

    val type = event.packetType

    val extraData = when(type) {
        Client.PLAYER_POSITION -> {
            val w = WrapperPlayClientPlayerPosition(event)
            "location=${w.location}|position=${w.position}|isOnGround=${w.isOnGround}|isHorizontalCollision=${w.isHorizontalCollision}"
        }
        Client.INTERACT_ENTITY -> {
            val w = WrapperPlayClientInteractEntity(event)
            "target=${w.target}|hand=${w.hand}|action=${w.action}|position=${w.isSneaking}|entityId=${w.entityId}"
        }
        Client.VEHICLE_MOVE -> {
            val w = WrapperPlayClientVehicleMove(event)
            "isOnGround=${w.isOnGround}|yaw=${w.yaw}|pitch=${w.pitch}|position=${w.position}"
        }
        Client.PLAYER_FLYING -> {
            val w = WrapperPlayClientPlayerFlying(event)
            "location=${w.location}|isOnGround=${w.isOnGround}|isHorizontalCollision=${w.isHorizontalCollision}"
        }
        else -> return false
    }

    return try {
        packetClientSendQueue.add("[$nowMs]|name=$name|uuid=$uuid|packet=${type.name}|$extraData")
        true
    } catch (_: Exception) { false }
}

/*fun getUnsafeDirectly(): Unsafe {
    val field = Unsafe::class.java.getDeclaredField("theUnsafe")
    field.isAccessible = true
    return field.get(null) as Unsafe
}

fun veryTesteste() {
    val unsafe = getUnsafeDirectly()
    val address = unsafe.allocateMemory(8L)

    try {
        // 2. 값 저장: 해당 주소에 1234567890L 이라는 큰 숫자를 씀
        unsafe.putLong(address, 1234567890L)

        // 3. 값 읽기: 그 주소에 가서 다시 데이터를 가져옴
        val savedValue = unsafe.getLong(address)
        println("메모리 주소 [$address]에 저장된 값: $savedValue")
    } finally {
        // 4. 메모리 해제: 다 썼으면 반드시 운영체제에 반납해야 함!
        // 이걸 안 하면 프로그램은 꺼져도 메모리는 계속 점유되는 '누수'가 생김
        unsafe.freeMemory(address)
        println("메모리 해제 완료")
    }
}
TODO: 나중에 만들어보고 싶네ㅇㅇ
*/