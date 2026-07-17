package _RedGold__.main.core.guild.chat

import _RedGold__.main.managers.playerData.PermissionEnum
import org.bukkit.entity.Player

private const val DEFAULT_COOLDOWN = 4L
private const val MVP_COOLDOWN = 0L

fun getChatCooldown(player: Player) =
    when(PermissionEnum[player]) {
        PermissionEnum.MVP -> MVP_COOLDOWN

        else -> DEFAULT_COOLDOWN
    }
