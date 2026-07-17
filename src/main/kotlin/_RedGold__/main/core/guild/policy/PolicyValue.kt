package _RedGold__.main.core.guild.policy

private typealias GuildId = Int

@Volatile var isAllowSearchGuilds = setOf<GuildId>()
@Volatile var isAllowChatGuilds = setOf<GuildId>()
@Volatile var isEnableWhitelistGuilds = setOf<GuildId>()
@Volatile var isAllowPvpGuilds = setOf<GuildId>()
