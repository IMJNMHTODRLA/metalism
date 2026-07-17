package _RedGold__.main.core.guild.settings.leader.whitelist

import _RedGold__.main.core.guild.settings.leader.whitelist.whitelistAdd.WhitelistAddGui
import _RedGold__.main.core.guild.settings.leader.whitelist.whitelistRemove.WhitelistRemoveGui
import _RedGold__.main.functions.NumberFormat.toUuidOrNull
import org.bukkit.entity.Player

fun autoDetectWhitelist(player: Player, payloadArg: String?, subArg: String?): Boolean {
    val targetUuid = subArg.toUuidOrNull()
    val isUuid = targetUuid != null

    when (payloadArg) {
        "추가", "add" -> {
            if (isUuid)
                WhitelistAddGui.openGui(player, targetUuid!!)
            else
                WhitelistAddGui.openGui(player, subArg?: return false)

            return true
        }

        "삭제", "remove" -> {
            if (isUuid)
                WhitelistRemoveGui.openGui(player, targetUuid!!)
            else
                WhitelistRemoveGui.openGui(player, subArg?: return false)

            return true
        }

        else -> return false
    }
}