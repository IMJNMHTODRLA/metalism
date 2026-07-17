package _RedGold__.main.core.guild.chat

import java.util.*

private typealias PlayerUuid = UUID
private typealias SecUnixTime = Long

val chatCooldownMap = mutableMapOf<PlayerUuid, SecUnixTime>()
