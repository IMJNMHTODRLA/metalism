package _RedGold__.main.core.guild

import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

private typealias PlayerUUID = UUID
private typealias GuildId = Int

val joinedGuildCache = ConcurrentHashMap<PlayerUUID, GuildId>()
