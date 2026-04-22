package _RedGold__.main.commands.user.rtp

import java.security.SecureRandom
import java.util.*

internal object RtpValue {
    val secureRandom = SecureRandom()
    val rtpCooldown = mutableMapOf<UUID, Long>()
}