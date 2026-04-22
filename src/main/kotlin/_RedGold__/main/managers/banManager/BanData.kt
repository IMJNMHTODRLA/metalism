package _RedGold__.main.managers.banManager

import java.util.*

data class BanData(
    var uuid: UUID,
    var type: BanEnum,
    var reason: String,
    var expiresAt: Long?,
    var bannedAt: Long
)