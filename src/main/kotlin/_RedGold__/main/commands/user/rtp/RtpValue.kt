package _RedGold__.main.commands.user.rtp

import java.util.*
import java.util.concurrent.ThreadLocalRandom

object RtpValue {
    val threadLocalRandom: ThreadLocalRandom = ThreadLocalRandom.current()
    val rtpCooldown = mutableMapOf<UUID, Long>()
}