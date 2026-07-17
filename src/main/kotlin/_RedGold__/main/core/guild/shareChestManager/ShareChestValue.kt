package _RedGold__.main.core.guild.shareChestManager

import org.bukkit.inventory.Inventory
import java.util.concurrent.ConcurrentHashMap

private typealias GuildId = Int

val shareChestData = ConcurrentHashMap<GuildId, Inventory>()
