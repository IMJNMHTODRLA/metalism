package _RedGold__.main.core.guild.join

import java.util.*
import java.util.concurrent.ConcurrentHashMap

private typealias GuildId = Int
val guildJoinLocked = ConcurrentHashMap.newKeySet<GuildId>()

private typealias PlayerUuid = UUID
private typealias Timestamp = Long
val guildJoinCooldown = ConcurrentHashMap<PlayerUuid, Timestamp>()
