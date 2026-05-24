package _RedGold__.main.core.guild.expManager

import java.util.concurrent.ConcurrentHashMap

private typealias GuildId = Int

private typealias Level = Int
val guildLevelCache = ConcurrentHashMap<GuildId, Level>()

private typealias ExpAmount = Long
val guildDelayAddExp = ConcurrentHashMap<GuildId, ExpAmount>()
